package com.example.SpringWeb.Service;

import com.example.SpringWeb.Model.Brand;
import com.example.SpringWeb.Model.Category;
import com.example.SpringWeb.Model.Product;
import com.example.SpringWeb.Repository.CategoryRepository;
import com.example.SpringWeb.Repository.ImageProductRepository;
import com.example.SpringWeb.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Autowired
    ImageProductRepository imageProductRepository;

    @Autowired
    ImageProductService imageProductService;

    // Show all products
    public Page<Product> getAllProducts(int page, int size) {
        Pageable sortedById = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "productId"));
        return productRepository.findAllByIsDeleted(sortedById, false);
    }

    public Page<Product> getProductsByFilter(int page, int size, Integer categoryId, String priceRange, Integer[] brandId, String[] color) {
        Pageable sortedById = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "productId"));
        Page<Product> products = null;
        boolean flag = false;
        String[] priceRanges = null;
        if(priceRange.equals("1000+")) {
            flag = true;
        }
        else {
            priceRanges = priceRange.split("-");
        }
        if(brandId != null) {
            if(color != null) {
                // Filter by category, price range, brand, and color
                if(flag) {
                    products = productRepository.findByCategory_CategoryIdAndPriceGreaterThanAndBrand_BrandIdInAndColorIn(categoryId, 1000, brandId, color, sortedById);
                }
                else {
                    products = productRepository.findByCategory_CategoryIdAndPriceBetweenAndBrand_BrandIdInAndColorIn(categoryId, Integer.parseInt(priceRanges[0]), Integer.parseInt(priceRanges[1]), brandId, color, sortedById);
                }
            }
            else {
                // Filter by category, price range, and brand
                if(flag) {
                    products = productRepository.findByCategory_CategoryIdAndPriceGreaterThanAndBrand_BrandIdIn(categoryId, 1000, brandId, sortedById);
                }
                else {
                    products = productRepository.findByCategory_CategoryIdAndPriceBetweenAndBrand_BrandIdIn(categoryId, Integer.parseInt(priceRanges[0]), Integer.parseInt(priceRanges[1]), brandId, sortedById);
                }
            }
        }
        else {
            if(color != null) {
                // Filter by category, price range, and color
                if(flag) {
                    products = productRepository.findByCategory_CategoryIdAndPriceGreaterThanAndColorIn(categoryId, 1000, color, sortedById);
                }
                else {
                    products = productRepository.findByCategory_CategoryIdAndPriceBetweenAndColorIn(categoryId, Integer.parseInt(priceRanges[0]), Integer.parseInt(priceRanges[1]), color, sortedById);
                }
            }
            else {
                // Filter by category and price range
                if(flag) {
                    products = productRepository.findByCategory_CategoryIdAndPriceGreaterThan(categoryId, 1000, sortedById);
                }
                else {
                    products = productRepository.findByCategory_CategoryIdAndPriceBetween(categoryId, Integer.parseInt(priceRanges[0]), Integer.parseInt(priceRanges[1]), sortedById);
                }
            }
        }

        return products;
    }

    // Show details of a product
    public Product getProductById(Integer productId) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        return optionalProduct.orElseThrow(() -> new RuntimeException("This product maybe doesn't exist anymore"));
    }

    public Page<Product> searchProducts(int page, int size, String keyword) {
        Pageable sortedById = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "productId"));
        return productRepository.findByNameContaining(keyword, sortedById);
    }

    // Save and update a product
    public Product saveProduct(Product product) {
        // Save all related images of the product
        imageProductService.saveImagesProduct(product.getImagesProduct());
        // Save the product
        return productRepository.save(product);
    }

    // Show all products by category
    public Page<Product> getProductsByCategory(Category category, int page, int size) {
        Pageable sortedById = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "productId"));
        return productRepository.findByCategory(category, sortedById);
    }

    // Show all products by brand
    public Page<Product> getProductsByBrand(Brand brand, int page, int size) {
        Pageable sortedById = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "productId"));
        return productRepository.findByBrand(brand, sortedById);
    }

    // Delete a product
    public void deleteProduct(Product product) {
        product.setDeleted(true);
        productRepository.save(product);
    }
}
