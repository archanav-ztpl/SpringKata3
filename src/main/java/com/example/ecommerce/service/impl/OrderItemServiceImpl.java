package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.OrderItemDto;
import com.example.ecommerce.entity.OrderItem;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.Product;
import com.example.ecommerce.repository.OrderItemRepository;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Override
    public OrderItemDto createOrderItem(OrderItemDto orderItemDto) {
        Order order = orderRepository.findById(orderItemDto.getId()).orElse(null);
        Product product = productRepository.findById(orderItemDto.getProductId()).orElse(null);
        OrderItem orderItem = OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(orderItemDto.getQuantity())
                .price(orderItemDto.getPrice())
                .build();
        OrderItem saved = orderItemRepository.save(orderItem);
        orderItemDto.setId(saved.getId());
        return orderItemDto;
    }

    @Override
    public OrderItemDto getOrderItemById(Long id) {
        return orderItemRepository.findById(id)
                .map(orderItem -> OrderItemDto.builder()
                        .id(orderItem.getId())
                        .productId(orderItem.getProduct() != null ? orderItem.getProduct().getId() : null)
                        .quantity(orderItem.getQuantity())
                        .price(orderItem.getPrice())
                        .build())
                .orElse(null);
    }

    @Override
    public List<OrderItemDto> getAllOrderItems() {
        return orderItemRepository.findAll().stream()
                .map(orderItem -> OrderItemDto.builder()
                        .id(orderItem.getId())
                        .productId(orderItem.getProduct() != null ? orderItem.getProduct().getId() : null)
                        .quantity(orderItem.getQuantity())
                        .price(orderItem.getPrice())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public OrderItemDto updateOrderItem(Long id, OrderItemDto orderItemDto) {
        return orderItemRepository.findById(id)
                .map(orderItem -> {
                    Product product = productRepository.findById(orderItemDto.getProductId()).orElse(null);
                    orderItem.setProduct(product);
                    orderItem.setQuantity(orderItemDto.getQuantity());
                    orderItem.setPrice(orderItemDto.getPrice());
                    OrderItem updated = orderItemRepository.save(orderItem);
                    orderItemDto.setId(updated.getId());
                    return orderItemDto;
                }).orElse(null);
    }

    @Override
    public void deleteOrderItem(Long id) {
        orderItemRepository.deleteById(id);
    }
}
