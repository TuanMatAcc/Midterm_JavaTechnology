package com.example.SpringWeb.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int brandId;
    private String name;
    @JsonManagedReference("brand-product")
    @OneToMany(mappedBy = "brand")
    private List<Product> products;

    public Brand(String name) {
        this.name = name;
    }
}
