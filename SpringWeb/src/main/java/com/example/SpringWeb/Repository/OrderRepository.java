package com.example.SpringWeb.Repository;

import com.example.SpringWeb.Model.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {
    public Page<Orders> findAllByIsDeleted(Pageable pageable, boolean deleted);
    public Page<Orders> findAllByIsDeletedAndStatus(Pageable pageable, boolean deleted, Orders.orderStatus status);
}
