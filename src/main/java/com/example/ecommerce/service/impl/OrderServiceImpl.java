package com.example.ecommerce.service.impl;

import com.example.ecommerce.dto.OrderDto;
import com.example.ecommerce.dto.OrderItemDto;
import com.example.ecommerce.entity.Order;
import com.example.ecommerce.entity.User;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Override
    public OrderDto createOrder(OrderDto orderDto) {
        User user = userRepository.findById(orderDto.getUserId()).orElse(null);
        Order order = Order.builder()
                .user(user)
                .orderDate(orderDto.getOrderDate())
                .status(orderDto.getStatus())
                .build();
        Order saved = orderRepository.save(order);
        orderDto.setId(saved.getId());
        return orderDto;
    }

    @Override
    public OrderDto getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(order -> OrderDto.builder()
                        .id(order.getId())
                        .userId(order.getUser() != null ? order.getUser().getId() : null)
                        .orderDate(order.getOrderDate())
                        .status(order.getStatus())
                        .build())
                .orElse(null);
    }

    @Override
    public List<OrderDto> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> OrderDto.builder()
                        .id(order.getId())
                        .userId(order.getUser() != null ? order.getUser().getId() : null)
                        .orderDate(order.getOrderDate())
                        .status(order.getStatus())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto updateOrder(Long id, OrderDto orderDto) {
        return orderRepository.findById(id)
                .map(order -> {
                    User user = userRepository.findById(orderDto.getUserId()).orElse(null);
                    order.setUser(user);
                    order.setOrderDate(orderDto.getOrderDate());
                    order.setStatus(orderDto.getStatus());
                    Order updated = orderRepository.save(order);
                    orderDto.setId(updated.getId());
                    return orderDto;
                }).orElse(null);
    }

    @Override
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}

