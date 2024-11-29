package com.example.SpringWeb.Service;

import com.example.SpringWeb.Model.Product;
import com.example.SpringWeb.Model.ProductOrdering;
import com.example.SpringWeb.Repository.OrderRepository;
import com.example.SpringWeb.Repository.ProductOrderingRepository;
import com.example.SpringWeb.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductOrderingService {
    @Autowired
    private ProductOrderingRepository productOrderingRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    public void saveProductOrdering(List<ProductOrdering> productsOrdering) {
        // Store all products to update stock later
        List<Product> productsOrderingList = new ArrayList<>();
        for(ProductOrdering po : productsOrdering) {
            if(po.getQuantity() <= 0) {
                orderRepository.delete(po.getOrder());
                throw new IllegalArgumentException("Quantity must be greater than 0");
            }
            if(po.getProduct() == null) {
                orderRepository.delete(po.getOrder());
                throw new IllegalArgumentException("Product must not be null");
            }
            if(po.getOrder() == null) {
                throw new IllegalArgumentException("Order must not be null");
            }
            if(!productRepository.existsByProductId(po.getProduct().getProductId())) {
                orderRepository.delete(po.getOrder());
                throw new IllegalArgumentException("Product with id: " + po.getProduct().getProductId() + " is not exists");
            }
            if(productRepository.getByProductId(po.getProduct().getProductId()).getStock() < po.getQuantity()) {
                orderRepository.delete(po.getOrder());
                throw new IllegalArgumentException("Product with id: " + po.getProduct().getProductId() + " is out of stock");
            }
            po.getProduct().setStock(po.getProduct().getStock() - po.getQuantity());
            productsOrderingList.add(po.getProduct());
        }
        // Add new products ordering
        productOrderingRepository.saveAll(productsOrdering);
        // Update stock for products
        productRepository.saveAll(productsOrderingList);
    }
}
