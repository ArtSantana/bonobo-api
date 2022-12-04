package com.api.bonobo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.bonobo.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
