package com.progaln.kopeeteria_backend.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.progaln.kopeeteria_backend.dto.ApiResponse;
import com.progaln.kopeeteria_backend.dto.OrderRequestDTO;
import com.progaln.kopeeteria_backend.dto.OrderResponseDTO;
import com.progaln.kopeeteria_backend.dto.TotalBillResponseDTO;
import com.progaln.kopeeteria_backend.services.OrderService;

@RestController
@RequestMapping("/kopeetearia-api")
@CrossOrigin(
    origins = "http://localhost:5173",
    allowedHeaders = "*",
    methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add-order")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> addOrder(@RequestBody OrderRequestDTO request) {
        OrderResponseDTO orderResponse = orderService.addOrder(request);

        ApiResponse<OrderResponseDTO> response = 
            new ApiResponse<>(true, "Order added successfully", orderResponse);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders")
    public ResponseEntity<ApiResponse<List<OrderResponseDTO>>> getAllOrders() {
        List<OrderResponseDTO> orders = orderService.getAllOrders();

        ApiResponse<List<OrderResponseDTO>> response = 
            new ApiResponse<>(true, "Orders fetched successfully", orders);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<OrderResponseDTO>> updateOrder(
        @PathVariable Long id,
        @RequestBody OrderRequestDTO request
    ) {
        OrderResponseDTO updatedOrder = orderService.updateOrder(id, request);

        ApiResponse<OrderResponseDTO> response =
            new ApiResponse<>(true, "Order updated successfully", updatedOrder);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);

        ApiResponse<Void> response =
            new ApiResponse<>(true, "Order deleted successfully", null);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/total-bill")
    public ResponseEntity<ApiResponse<TotalBillResponseDTO>> calculateTotals() {
        TotalBillResponseDTO totals = orderService.calculateTotals();

        ApiResponse<TotalBillResponseDTO> response =
            new ApiResponse<>(true, "Totals calculated successfully", totals);

        return ResponseEntity.ok(response);
    }
}