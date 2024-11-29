package com.example.SpringWeb.Repository;

import com.example.SpringWeb.Model.ImageProduct;
import com.example.SpringWeb.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.*;
import java.util.List;

@Repository
public interface ImageProductRepository extends JpaRepository<ImageProduct, Integer> {
    public List<ImageProduct> findByProduct(Product product);
    public void deleteByProduct(Product product);
}
