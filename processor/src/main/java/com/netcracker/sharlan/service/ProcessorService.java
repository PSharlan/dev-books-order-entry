package com.netcracker.sharlan.service;

import com.netcracker.sharlan.dto.catalog.OfferDto;
import com.netcracker.sharlan.dto.inventory.OrderDto;
import com.netcracker.sharlan.dto.inventory.OrderItemDto;

import java.util.List;

public interface ProcessorService {

    OrderDto generateOrder(Long userId, List<OfferDto> offers);

    OrderItemDto generateItem(OfferDto offer);

    double calculateOrdersPrice(List<OrderDto> orders);

}
