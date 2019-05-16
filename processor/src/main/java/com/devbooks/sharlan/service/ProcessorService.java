package com.devbooks.sharlan.service;

import com.devbooks.sharlan.dto.catalog.OfferDto;
import com.devbooks.sharlan.dto.inventory.OrderDto;
import com.devbooks.sharlan.dto.inventory.OrderItemDto;

import java.util.List;

public interface ProcessorService {

    OrderDto generateOrder(Long userId, List<OfferDto> offers);

    OrderItemDto generateItem(OfferDto offer);

    double calculateOrdersPrice(List<OrderDto> orders);

}
