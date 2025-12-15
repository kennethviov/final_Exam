package com.progaln.kopeeteria_backend.services;

import java.util.List;

import com.progaln.kopeeteria_backend.dto.OrderRequestDTO;
import com.progaln.kopeeteria_backend.dto.OrderResponseDTO;
import com.progaln.kopeeteria_backend.dto.TotalBillResponseDTO;

public interface OrderService {

    OrderResponseDTO addOrder(OrderRequestDTO dtpo);

    List<OrderResponseDTO> getAllOrders();

    OrderResponseDTO updateOrder(Long id, OrderRequestDTO dto);

    void deleteOrder(Long id);

    TotalBillResponseDTO calculateTotals();

}
