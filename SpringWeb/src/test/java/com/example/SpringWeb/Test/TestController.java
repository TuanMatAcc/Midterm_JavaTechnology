package com.example.SpringWeb.Test;

import com.example.SpringWeb.Model.ImageProduct;
import com.example.SpringWeb.Model.Product;
import java.util.ArrayList;
import com.example.SpringWeb.Service.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestController {
    ProductService productsService = new ProductService();
    OrderService orderService = new OrderService();
    UserService userService = new UserService();
    BrandService brandService = new BrandService();
    CategoryService categoryService = new CategoryService();
    ImageProductService imageProductService = new ImageProductService();
    ProductOrderingService productOrderingService = new ProductOrderingService();

    @Test
    void Test1() {
        Product product1 = new Product("iPhone 14", 999, 50, "Black", null, null, "Latest Apple phone with A16 Bionic chip", new ArrayList<ImageProduct>());
        assertEquals(productsService.saveProduct(product1).getImagesProduct().size(), 0);
    }

    @Test
    void Test2() {
        Product product1 = new Product("iPhone 14", 999, 50, "Black", null, null, "Latest Apple phone with A16 Bionic chip", new ArrayList<ImageProduct>());
        product1.getImagesProduct().add(new ImageProduct());
        assertEquals(productsService.saveProduct(product1).getImagesProduct().size(), 1);
    }
}
