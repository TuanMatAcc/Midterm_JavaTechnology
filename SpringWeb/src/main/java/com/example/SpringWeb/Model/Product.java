package com.example.SpringWeb.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int productId;
    private String name;
    private int price;
    private int stock;
    private String color;

    @JsonBackReference("brand-product")
    @ManyToOne
    @JoinColumn(name = "brandId")
    private Brand brand;

    @JsonBackReference("category-product")
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    private String description;

    @JsonManagedReference("product-image")
    @OneToMany(mappedBy = "product")
    private List<ImageProduct> imagesProduct;
    private boolean isDeleted;

    public Product(String name, int price, int stock, String color, Brand brand, Category category, String description, List<ImageProduct> imagesProduct) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.color = color;
        this.brand = brand;
        this.category = category;
        this.description = description;
        this.imagesProduct = imagesProduct;
    }
}
