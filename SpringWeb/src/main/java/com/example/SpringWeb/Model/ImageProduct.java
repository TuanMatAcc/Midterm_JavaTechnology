package com.example.SpringWeb.Model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_image")
public class ImageProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int imageId;
    private String url;
    @JsonBackReference("product-image")
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
