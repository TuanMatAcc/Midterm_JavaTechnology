package com.example.SpringWeb.Model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartItem {
    private Product product;
    private int quantity;
    private double total;
}
