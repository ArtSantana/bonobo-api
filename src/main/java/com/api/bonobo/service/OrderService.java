package com.api.bonobo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.springframework.stereotype.Service;

import com.api.bonobo.exception.InvalidFieldsException;
import com.api.bonobo.exception.NotFoundException;
import com.api.bonobo.model.Order;
import com.api.bonobo.model.OrderStatus;
import com.api.bonobo.model.Product;
import com.api.bonobo.repository.OrderRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;

    public List<Order> findAll() {
       return orderRepository.findAll();
    }

    public void create(Order order) {
       validateStockOfProducts(order.getProducts());

       order.setPaidPrice(calculatePrice(order.getProducts()));
       order.setOrderStatus(OrderStatus.WAITING_PAYMENT);

       orderRepository.save(order);
    }

    private void validateStockOfProducts(List<Product> products) {
       List<String> messages = new ArrayList<>();

       products.forEach(p -> {
           if (productService.outOfStock(p.getId())) {
               messages.add(String.format("Product % out of stock", p.getName()));
           }
       });

       if (!messages.isEmpty()) {
           throw new InvalidFieldsException(messages);
       }
    }

    private Double calculatePrice(List<Product> products) {
        AtomicReference<Double> paidPrice = new AtomicReference<>(0d);

        products.forEach(p -> paidPrice.updateAndGet(v -> v + p.getPrice()));

        return paidPrice.get();
    }

    public Order findById(Long id) {
        Optional<Order> order = orderRepository.findById(id);

        if (order.isEmpty()) {
           throw new NotFoundException("Order not found!");
        }

        return order.get();
    }
}
