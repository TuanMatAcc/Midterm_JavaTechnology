package com.example.SpringWeb.Repository;

import com.example.SpringWeb.Model.Brand;
import com.example.SpringWeb.Model.Category;
import com.example.SpringWeb.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    public Page<Product> findAllByIsDeleted(Pageable pageable, boolean deleted);
    public boolean existsByProductId(Integer productId);
    public Product getByProductId(Integer productId);
    public Page<Product> findByNameContaining(String search, Pageable pageable);
    public Page<Product> findByBrand(Brand brand, Pageable pageable);
    public Page<Product> findByCategory(Category category, Pageable pageable);
    public Page<Product> findByCategory_CategoryIdAndPriceBetweenAndBrand_BrandIdInAndColorIn(Integer categoryId, Integer minPrice, Integer maxPrice, Integer[] brandId , String[] color, Pageable pageable);
    public Page<Product> findByCategory_CategoryIdAndPriceBetweenAndBrand_BrandIdIn(Integer categoryId, Integer minPrice, Integer maxPrice, Integer[] brandId, Pageable pageable);
    public Page<Product> findByCategory_CategoryIdAndPriceBetweenAndColorIn(Integer categoryId, Integer minPrice, Integer maxPrice, String[] color, Pageable pageable);
    public Page<Product> findByCategory_CategoryIdAndPriceBetween(Integer categoryId, Integer minPrice, Integer maxPrice, Pageable pageable);
    public Page<Product> findByCategory_CategoryIdAndPriceGreaterThanAndBrand_BrandIdInAndColorIn(Integer categoryId, Integer minPrice, Integer[] brandId, String[] color, Pageable pageable);
    public Page<Product> findByCategory_CategoryIdAndPriceGreaterThanAndBrand_BrandIdIn(Integer categoryId, Integer minPrice, Integer[] brandId, Pageable pageable);
    public Page<Product> findByCategory_CategoryIdAndPriceGreaterThanAndColorIn(Integer categoryId, Integer minPrice, String[] color, Pageable pageable);
    public Page<Product> findByCategory_CategoryIdAndPriceGreaterThan(Integer categoryId, Integer minPrice, Pageable pageable);
}
