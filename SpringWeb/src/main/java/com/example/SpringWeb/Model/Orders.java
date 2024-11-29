package com.example.SpringWeb.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int orderId;
    private Date orderDate;
    private orderStatus status;
    private String address;
    private String phone;
    private String name;
    public enum orderStatus {
        PENDING,
        SHIPPED,
        DELIVERED
    }
    @ManyToOne
    @JoinColumn(name = "nameAccount", nullable = true)
    private User user;
    @OneToMany(mappedBy = "order")
    private List<ProductOrdering> products;
    private boolean isDeleted;
}
