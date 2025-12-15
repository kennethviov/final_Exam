package com.progaln.kopeeteria_backend.services.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.progaln.kopeeteria_backend.dto.OrderRequestDTO;
import com.progaln.kopeeteria_backend.dto.OrderResponseDTO;
import com.progaln.kopeeteria_backend.dto.TotalBillResponseDTO;
import com.progaln.kopeeteria_backend.models.Order;
import com.progaln.kopeeteria_backend.repo.OrderRepository;
import com.progaln.kopeeteria_backend.services.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderResponseDTO addOrder(OrderRequestDTO dto) {
        Order order = new Order();
        order.setOrderName(dto.getOrderName());
        order.setPrice(dto.getPrice());

        if (dto.isDiscounted() == true) {
            order.setDiscounted(dto.isDiscounted());
        } else if (dto.isDiscounted() != true && dto.isDiscounted() != false){
            order.setDiscounted(false);
        }

        order.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);

        return mapToResponseDTO(savedOrder);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
        .map(this::mapToResponseDTO)
        .toList();
    }

    @Override
    public OrderResponseDTO updateOrder(Long id, OrderRequestDTO dto) {
        Order order = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setOrderName(dto.getOrderName());
        order.setPrice(dto.getPrice());
        order.setDiscounted(dto.isDiscounted());

        Order updatedOrder = orderRepository.save(order);

        return mapToResponseDTO(updatedOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new RuntimeException("Order not found");
        }
        orderRepository.deleteById(id);
    }

    @Override
    public TotalBillResponseDTO calculateTotals() {
        List<Order> orders = orderRepository.findAll();

        double regularTotal = orders.stream()
            .mapToDouble(Order::getPrice)
            .sum();

        double discountedTotal = orders.stream()
            .filter(Order::isDiscounted)
            .mapToDouble(order -> order.getPrice() * 0.95)
            .sum();

        TotalBillResponseDTO totals = new TotalBillResponseDTO();
        totals.setRegularBillTotal(regularTotal);
        totals.setDiscountedBillTotal(discountedTotal);

        return totals;
    }

    private OrderResponseDTO mapToResponseDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setId(order.getId());
        dto.setOrderName(order.getOrderName());
        dto.setPrice(order.getPrice());
        dto.setDiscounted(order.isDiscounted());
        return dto;
    }

}
