package com.netcracker.sharlan.controller;

import com.netcracker.sharlan.dto.catalog.OfferDto;
import com.netcracker.sharlan.dto.customer.UserDto;
import com.netcracker.sharlan.dto.inventory.OrderDto;
import com.netcracker.sharlan.dto.inventory.OrderItemDto;
import com.netcracker.sharlan.services.ProcessorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;


@RestController
@RequestMapping("/api/v1/processor")
@Api(value = "/api/v1/processor", description = "Manage purchases")
public class ProcessorController {

    private static final Logger LOGGER = LogManager.getLogger(ProcessorController.class.getName());

    private final RestTemplate restTemplate;

    private final String catalogUrl;
    private final String inventoryUrl;
    private final String customerUrl;

    private ProcessorService processorService;

    @Autowired
    public ProcessorController(RestTemplate restTemplate,
                               @Value("${catalog.url}") String catalogUrl,
                               @Value("${inventory.url}") String inventoryUrl,
                               @Value("${customer-management.url}") String customerUrl,
                               ProcessorService processorService) {
        this.restTemplate = restTemplate;
        this.catalogUrl = catalogUrl;
        this.inventoryUrl = inventoryUrl;
        this.customerUrl = customerUrl;
        this.processorService = processorService;
    }

    @ApiOperation(
            value = "Create new order",
            notes = "User id and List of offers are required"
    )
    @RequestMapping(value = "/user/{userId}/orders", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(
            @ApiParam(value = "Ids of offers to purchase", required = true)
            @RequestBody List<Long> offerIds,
            @ApiParam(value = "User id", required = true)
            @PathVariable long userId) {

        LOGGER.info("Creating a new order | User id: " + userId + " | Offer ids: " + offerIds);

        //get offers from catalog-module
        UriComponentsBuilder catalogUriBuilder = UriComponentsBuilder.fromHttpUrl(catalogUrl + "/offers/list")
                .queryParam("ids", offerIds.toArray());
        List<OfferDto> offers = restTemplate.exchange(catalogUriBuilder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<OfferDto>>() {
                }).getBody();

        LOGGER.info("Found offers: " + offers);
        OrderDto order = processorService.generateOrder(userId, offers);
        LOGGER.info("Generated order: " + order);

        //create new order by inventory-module
        OrderDto savedOrder = restTemplate.exchange(inventoryUrl + "/orders",
                HttpMethod.POST, new HttpEntity<>(order), new ParameterizedTypeReference<OrderDto>() {
                }).getBody();

        return savedOrder;
    }

    @ApiOperation(
            value = "Add new item to existing order",
            notes = "List of categories is required"
    )
    @RequestMapping(value = "/orders/{orderId}/items", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto addNewOrderItem(
            @ApiParam(value = "Ids of offer to add", required = true)
            @RequestBody long offerId,
            @ApiParam(value = "Order id", required = true)
            @PathVariable long orderId) {

        LOGGER.info("Add new order item | Order id: " + orderId + " | Offer id: " + offerId);

        //get offer from catalog-module
        UriComponentsBuilder catalogUriBuilder = UriComponentsBuilder.fromHttpUrl(catalogUrl + "/offers/" + offerId);
        LOGGER.info("URL: " + catalogUriBuilder.toUriString());
        OfferDto offer = restTemplate.exchange(catalogUriBuilder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<OfferDto>() {
                }).getBody();

        //get order from inventory-module
        UriComponentsBuilder inventoryUriBuilder = UriComponentsBuilder.fromHttpUrl(inventoryUrl + "/orders/" + orderId);
        LOGGER.info("URL: " + inventoryUriBuilder.toUriString());
        OrderDto order = restTemplate.exchange(inventoryUriBuilder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<OrderDto>() {
                }).getBody();

        LOGGER.info("Found order: " + order);
        LOGGER.info("Found offer: " + offer);
        OrderItemDto item = processorService.generateItem(offer);
        LOGGER.info("Generated item: " + item);
        order.getItems().add(item);

        //create new order item by inventory-module and update order
        OrderDto savedOrder = restTemplate.exchange(inventoryUrl + "/orders",
                HttpMethod.PUT, new HttpEntity<>(order), new ParameterizedTypeReference<OrderDto>() {
                }).getBody();

        return savedOrder;
    }

    @ApiOperation(
            value = "Return all orders by customer email",
            notes = "User email required"
    )
    @RequestMapping(value = "/user/{email}/orders", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> getOrdersByEmail(
            @ApiParam(value = "User email", required = true)
            @PathVariable String email) {

        LOGGER.info("Search for orders by email: " + email);

        //get user id from customer-module
        UriComponentsBuilder customerUriBuilder = UriComponentsBuilder.fromHttpUrl(customerUrl + "/email/" + email);
        UserDto user = restTemplate.exchange(customerUriBuilder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<UserDto>() {
                }).getBody();
        Long userId = user.getId();

        LOGGER.info("Found user id: " + userId);

        //get orders from inventory-module
        UriComponentsBuilder inventoryUriBuilder = UriComponentsBuilder.fromHttpUrl(inventoryUrl + "/customers/" + userId + "/orders");
        List<OrderDto> orders = restTemplate.exchange(inventoryUriBuilder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderDto>>() {
                }).getBody();

        LOGGER.info("Found orders: " + orders);

        return orders;
    }

    @ApiOperation(
            value = "Return total price of orders by customer email",
            notes = "User email required"
    )
    @RequestMapping(value = "/user/{email}/orders/price", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Double getOrdersPriceByEmail(
            @ApiParam(value = "User email", required = true)
            @PathVariable String email) {

        LOGGER.info("Search for orders by email: " + email);

        //get user id from customer-module
        UriComponentsBuilder customerUriBuilder = UriComponentsBuilder.fromHttpUrl(customerUrl + "/email/" + email);
        UserDto user = restTemplate.exchange(customerUriBuilder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<UserDto>() {
                }).getBody();
        Long userId = user.getId();

        LOGGER.info("Found user id: " + userId);

        //get orders from inventory-module
        UriComponentsBuilder inventoryUriBuilder = UriComponentsBuilder.fromHttpUrl(inventoryUrl + "/customers/" + userId + "/orders");
        List<OrderDto> orders = restTemplate.exchange(inventoryUriBuilder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderDto>>() {
                }).getBody();

        LOGGER.info("Found orders: " + orders);

        double totalPrice = 0;
        for (OrderDto order : orders) {
            if(!order.getOrderStatus().toLowerCase().equals("canceled")) {
                totalPrice += order.getPriceTotal();
            }
        }

        return totalPrice;
    }

    @ApiOperation(
            value = "Return total amount of orders by customer email",
            notes = "User email required"
    )
    @RequestMapping(value = "/user/{email}/orders/amount", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Integer getOrdersAmountByEmail(
            @ApiParam(value = "User email", required = true)
            @PathVariable String email) {

        LOGGER.info("Search for orders by email: " + email);

        //get user id from customer-module
        UriComponentsBuilder customerUriBuilder = UriComponentsBuilder.fromHttpUrl(customerUrl + "/email/" + email);
        UserDto user = restTemplate.exchange(customerUriBuilder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<UserDto>() {
                }).getBody();
        Long userId = user.getId();

        LOGGER.info("Found user id: " + userId);

        //get orders from inventory-module
        UriComponentsBuilder inventoryUriBuilder = UriComponentsBuilder.fromHttpUrl(inventoryUrl + "/customers/" + userId + "/orders");
        List<OrderDto> orders = restTemplate.exchange(inventoryUriBuilder.toUriString(),
                HttpMethod.GET, null, new ParameterizedTypeReference<List<OrderDto>>() {
                }).getBody();

        LOGGER.info("Found orders: " + orders);

        int totalAmount = 0;
        for (OrderDto order : orders) {
            if(!order.getOrderStatus().toLowerCase().equals("canceled")) {
                totalAmount++;
            }
        }

        return totalAmount;
    }



}
