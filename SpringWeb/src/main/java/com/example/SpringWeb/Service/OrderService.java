package com.example.SpringWeb.Service;

import com.example.SpringWeb.Model.Orders;
import com.example.SpringWeb.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public void saveOrder(Orders order) {
        if(order.getOrderDate() == null) {
            order.setOrderDate(new Date());
        }
        if(order.getStatus() == null) {
            order.setStatus(Orders.orderStatus.PENDING);
        }
        if(order.getName() == null || order.getName().isEmpty()) {
            throw new IllegalArgumentException("Name must not be empty");
        }
        if(!isStringOnlyAlphabet(order.getName())) {
            throw new IllegalArgumentException("Name must contain only alphabets");
        }
        if(order.getPhone() == null || order.getPhone().isEmpty()) {
            throw new IllegalArgumentException("Phone must not be empty");
        }
        try {
            if(Double.parseDouble(order.getPhone()) < 0) {
                throw new IllegalArgumentException("Phone must be a positive number");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Phone must be a number");
        }
        if(order.getAddress() == null || order.getAddress().isEmpty()) {
            throw new IllegalArgumentException("Address must not be empty");
        }
        orderRepository.save(order);
    }

    public boolean isStringOnlyAlphabet(String str)
    {
        return (!str.isEmpty() && str.matches("^[\\p{L} ]*$"));
    }

    public Page<Orders> getAllOrders(int page, int size) {
        Pageable sortedById = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "orderId"));
        return orderRepository.findAllByIsDeleted(sortedById, false);
    }

    public Page<Orders> getAllPendingOrders(int page, int size) {
        Pageable sortedById = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "orderId"));
        return orderRepository.findAllByIsDeletedAndStatus(sortedById, false, Orders.orderStatus.PENDING);
    }

    public Page<Orders> getAllShippedOrders(int page, int size) {
        Pageable sortedById = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "orderId"));
        return orderRepository.findAllByIsDeletedAndStatus(sortedById, false, Orders.orderStatus.SHIPPED);
    }

    public Page<Orders> getAllDeliveredOrders(int page, int size) {
        Pageable sortedById = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "orderId"));
        return orderRepository.findAllByIsDeletedAndStatus(sortedById, false, Orders.orderStatus.DELIVERED);
    }

    public Orders getOrderById(int orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }
}
