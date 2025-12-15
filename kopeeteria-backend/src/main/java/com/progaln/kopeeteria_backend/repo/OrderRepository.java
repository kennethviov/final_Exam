package com.progaln.kopeeteria_backend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.progaln.kopeeteria_backend.models.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
