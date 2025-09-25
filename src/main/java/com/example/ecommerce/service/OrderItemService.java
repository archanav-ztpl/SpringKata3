package com.example.ecommerce.service;

import com.example.ecommerce.dto.OrderItemDto;
import java.util.List;

public interface OrderItemService {
    OrderItemDto createOrderItem(OrderItemDto orderItemDto);
    OrderItemDto getOrderItemById(Long id);
    List<OrderItemDto> getAllOrderItems();
    OrderItemDto updateOrderItem(Long id, OrderItemDto orderItemDto);
    void deleteOrderItem(Long id);
}

