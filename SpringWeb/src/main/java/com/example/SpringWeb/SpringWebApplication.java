package com.example.SpringWeb;

import com.example.SpringWeb.Model.*;
import com.example.SpringWeb.Service.BrandService;
import com.example.SpringWeb.Service.CategoryService;
import com.example.SpringWeb.Service.ProductService;
import com.example.SpringWeb.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class SpringWebApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebApplication.class, args);
	}

	@Autowired
	ProductService productService;
	@Autowired
	BrandService brandService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	UserService userService;

	public void run(String... args) {
		Brand apple = new Brand("Apple");
		Brand samsung = new Brand("Samsung");
		Brand dell = new Brand("Dell");

		// Define Categories
		Category phones = new Category("Phone");
		Category laptops = new Category("Laptop");

		brandService.saveBrand(apple);
		brandService.saveBrand(samsung);
		brandService.saveBrand(dell);

		categoryService.saveCategory(phones);
		categoryService.saveCategory(laptops);

		// Define Products
		Product product1 = new Product("iPhone 14", 999, 50, "Black", apple, phones, "Latest Apple phone with A16 Bionic chip", new ArrayList<ImageProduct>());
		Product product2 = new Product("Samsung Galaxy S23", 849, 30, "White", samsung, phones, "Flagship Samsung phone with Snapdragon 8 Gen 2", new ArrayList<ImageProduct>());
		Product product3 = new Product("MacBook Air M2", 1199, 20, "Orange", apple, laptops, "Lightweight laptop with M2 chip", new ArrayList<ImageProduct>());
		Product product4 = new Product("Dell XPS 13", 1399, 25, "Pink", dell, laptops, "Compact laptop with 13-inch InfinityEdge display", new ArrayList<ImageProduct>());
		Product product5 = new Product("iPhone SE", 429, 40, "Blue", apple, phones, "Affordable iPhone with A15 Bionic chip", new ArrayList<ImageProduct>());
		Product product6 = new Product("Samsung Galaxy Z Fold 4", 1799, 15, "Black", samsung, phones, "Foldable phone with AMOLED display", new ArrayList<ImageProduct>());
		Product product7 = new Product("MacBook Pro M2", 2499, 10, "Yellow", apple, laptops, "Powerful laptop with M2 Pro chip", new ArrayList<ImageProduct>());
		Product product8 = new Product("Dell Inspiron 15", 799, 35, "Black", dell, laptops, "Budget-friendly laptop with 15-inch display", new ArrayList<ImageProduct>());
		Product product9 = new Product("Samsung Galaxy A53", 349, 60, "Blue", samsung, phones, "Mid-range phone with 120Hz AMOLED display", new ArrayList<ImageProduct>());
		Product product10 = new Product("MacBook Air M1", 999, 15, "Blue", apple, laptops, "Affordable MacBook with M1 chip", new ArrayList<ImageProduct>());
		Product product11 = new Product("Dell Latitude 7420", 1299, 20, "Black", dell, laptops, "Business laptop with long battery life", new ArrayList<ImageProduct>());
		Product product12 = new Product("Samsung Galaxy Note 20", 1049, 18, "White", samsung, phones, "Premium phone with S-Pen support", new ArrayList<ImageProduct>());
		Product product13 = new Product("MacBook Air M3", 1299, 20, "Black", apple, laptops, "Lightweight laptop with M3 chip", new ArrayList<ImageProduct>());
		Product product14 = new Product("MacBook Air M4", 1399, 20, "Blue", apple, laptops, "Lightweight laptop with M4 chip", new ArrayList<ImageProduct>());
		Product product15 = new Product("MacBook Air M5", 1499, 20, "Pink", apple, laptops, "Lightweight laptop with M5 chip", new ArrayList<ImageProduct>());

		productService.saveProduct(product1);
		productService.saveProduct(product2);
		productService.saveProduct(product3);
		productService.saveProduct(product4);
		productService.saveProduct(product5);
		productService.saveProduct(product6);
		productService.saveProduct(product7);
		productService.saveProduct(product8);
		productService.saveProduct(product9);
		productService.saveProduct(product10);
		productService.saveProduct(product11);
		productService.saveProduct(product12);
		productService.saveProduct(product13);
		productService.saveProduct(product14);
		productService.saveProduct(product15);

		product1.getImagesProduct().add(new ImageProduct(1, "/images/iphone14.jpeg", product1));
		product1.getImagesProduct().add(new ImageProduct(2, "/images/iphone14.jpeg", product1));
		product2.getImagesProduct().add(new ImageProduct(3, "/images/ip16-pur.jpeg", product2));
		product3.getImagesProduct().add(new ImageProduct(4, "/images/ip16-pur.jpeg", product3));
		product4.getImagesProduct().add(new ImageProduct(5, "/images/ip16-pur.jpeg", product4));
		product5.getImagesProduct().add(new ImageProduct(6, "/images/ip16-pur.jpeg", product5));
		product6.getImagesProduct().add(new ImageProduct(7, "/images/ip16-pur.jpeg", product6));
		product7.getImagesProduct().add(new ImageProduct(8, "/images/ip16-pur.jpeg", product7));
		product8.getImagesProduct().add(new ImageProduct(9, "/images/ip16-pur.jpeg", product8));
		product9.getImagesProduct().add(new ImageProduct(10, "/images/ip16-pur.jpeg", product9));
		product10.getImagesProduct().add(new ImageProduct(11, "/images/ip16-pur.jpeg", product10));
		product11.getImagesProduct().add(new ImageProduct(12, "/images/ip16-pur.jpeg", product11));
		product12.getImagesProduct().add(new ImageProduct(13, "/images/iphone14.jpeg", product12));
		product12.getImagesProduct().add(new ImageProduct(14, "/images/ip16-pur.jpeg", product12));
		product13.getImagesProduct().add(new ImageProduct(15, "/images/ip16-pur.jpeg", product13));
		product14.getImagesProduct().add(new ImageProduct(16, "/images/ip16-pur.jpeg", product14));
		product15.getImagesProduct().add(new ImageProduct(17, "/images/ip16-pur.jpeg", product15));

		productService.saveProduct(product1);
		productService.saveProduct(product2);
		productService.saveProduct(product3);
		productService.saveProduct(product4);
		productService.saveProduct(product5);
		productService.saveProduct(product6);
		productService.saveProduct(product7);
		productService.saveProduct(product8);
		productService.saveProduct(product9);
		productService.saveProduct(product10);
		productService.saveProduct(product11);
		productService.saveProduct(product12);
		productService.saveProduct(product13);
		productService.saveProduct(product14);
		productService.saveProduct(product15);
		User user = new User();
		user.setNameAccount("admin");
		user.setPassword("1234");
		user.setRole(User.role.ADMIN);
		userService.saveUser(user);
		User user1 = new User();
		user1.setNameAccount("user");
		user1.setPassword("1234");
		user1.setRole(User.role.USER);
		userService.saveUser(user1);
	}

}
