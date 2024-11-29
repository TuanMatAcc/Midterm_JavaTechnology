package com.example.SpringWeb.Controller;

import com.example.SpringWeb.Model.Brand;
import com.example.SpringWeb.Model.Category;
import com.example.SpringWeb.Model.ImageProduct;
import com.example.SpringWeb.Model.Product;
import com.example.SpringWeb.Service.BrandService;
import com.example.SpringWeb.Service.CategoryService;
import com.example.SpringWeb.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private CategoryService categoryService;

//    public ProductsController(ProductService productService, BrandService brandService, CategoryService categoryService) {
//        this.productService = productService;
//        this.brandService = brandService;
//        this.categoryService = categoryService;
//    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "8") int size,
                                 @RequestParam(defaultValue = "", name = "keyword") String search, Model model) {
        Page<Product> productPage = productService.searchProducts(page, size,search);
        List<Product> allProducts = productPage.getContent();
        List<Product> products = new ArrayList<>();
        for(Product product : allProducts) {
            if(!product.isDeleted()) {
                products.add(product);
            }
        }
        ArrayList<ArrayList<String>> productImages = new ArrayList<>();
        for(Product product : products) {
            ArrayList<String> images = new ArrayList<>();
            for(int i = 0; i < product.getImagesProduct().size(); i++) {
                images.add(product.getImagesProduct().get(i).getUrl());
            }
            productImages.add(images);
        }

        model.addAttribute("productImages", productImages);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        return "products-search";
    }

    @GetMapping("/list")
    public String showProductPage(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "6") int size,
                                  @RequestParam(required = false, name = "category") Integer categoryId,
                                  @RequestParam(required = false, name = "price") String priceRange,
                                  @RequestParam(required = false, name = "brand") Integer[] brandId,
                                  @RequestParam(required = false) String[] color,
                                  Model model) {
        loadBrands(model);
        int defaultCategoryId = loadCategories(model).getFirst().getCategoryId();
        if(categoryId == null) {
            categoryId = defaultCategoryId;
        }
        if(priceRange == null) {
            priceRange = "0-100";
        }
        loadProducts(page, size, categoryId, priceRange, brandId, color, model);
        // Set default values for brandId and color
        if(brandId == null) {
            brandId = new Integer[0];
        }
        if(color == null) {
            color = new String[0];
        }
        List<Integer> brands = Arrays.asList(brandId);
        List<String> colors = Arrays.asList(color);
        // Store the selected options
        model.addAttribute("selectedCategoryId", categoryId);
        model.addAttribute("selectedPriceRange", priceRange);
        model.addAttribute("selectedBrandIds", brands);
        model.addAttribute("selectedColors", colors);
        return "product-list";
    }

    public void loadProducts(int page, int size, Integer categoryId, String priceRange, Integer[] brandId, String[] color, Model model) {
        Page<Product> productPage = productService.getProductsByFilter(page, size, categoryId, priceRange, brandId, color);
        if(productPage == null) {
            return;
        }
        List<Product> allProducts = productPage.getContent();
        List<Product> products = new ArrayList<>();
        for(Product product : allProducts) {
            if(!product.isDeleted()) {
                products.add(product);
            }
        }
        ArrayList<ArrayList<String>> productImages = new ArrayList<>();
        for(Product product : products) {
            ArrayList<String> images = new ArrayList<>();
            for(int i = 0; i < product.getImagesProduct().size(); i++) {
                images.add(product.getImagesProduct().get(i).getUrl());
            }
            productImages.add(images);
        }

        model.addAttribute("productImages", productImages);
        model.addAttribute("products", products);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
    }

    public List<Brand> loadBrands(Model model) {
        List<Brand> brands = brandService.getAllBrands();
        model.addAttribute("brands", brands);
        return brands;
    }

    public List<Category> loadCategories(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        return categories;
    }

//    @GetMapping("/login")
//    public String getLogin() {
//        return "login";
//    }

    @GetMapping("/products")
    public RedirectView redirectWithUsingRedirectView(
            RedirectAttributes attributes) {
        attributes.addFlashAttribute("flashAttribute", "redirectWithRedirectView");
        attributes.addAttribute("attribute", "redirectWithRedirectView");
        return new RedirectView("redirectedUrl");
    }

    @GetMapping("/management/list-all")
    public String listAllProducts( @RequestParam (defaultValue = "0") int page,
                                   @RequestParam (defaultValue = "6") int size,
                                   Model model) {
        Page<Product> products = productService.getAllProducts(page, size);
        loadBrands(model);
        loadCategories(model);
        model.addAttribute("products", products.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", products.getTotalPages());
        return "product-management";
    }

    @GetMapping("/management/list/{id}")
    public String listProduct(@PathVariable int id ,Model model) {
        Product product = productService.getProductById(id);
        loadBrands(model);
        loadCategories(model);
        model.addAttribute("product", product);
        return "product-update";
    }

    @PostMapping("/management/insert")
    public RedirectView insertProduct(@RequestParam("name") String name,
                                      @RequestParam("price") int price,
                                      @RequestParam("stock") int stock,
                                      @RequestParam("color") String color,
                                      @RequestParam("brand") int brandId,
                                      @RequestParam("category") int categoryId,
                                      @RequestParam("description") String description,
                                      @RequestParam("images") String[] images) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setColor(color);
        product.setBrand(brandService.getBrandById(brandId));
        product.setCategory(categoryService.getCategoryById(categoryId));
        product.setDescription(description);
        product.setImagesProduct(new ArrayList<>());
        Product addedProduct = productService.saveProduct(product);

        List<ImageProduct> imagesList = new ArrayList<>();
        for(String image : images) {
            ImageProduct imageProduct = new ImageProduct();
            imageProduct.setUrl("/images/"+ image);
            imageProduct.setProduct(addedProduct);
            imagesList.add(imageProduct);
        }
        addedProduct.setImagesProduct(imagesList);
        productService.saveProduct(addedProduct);
        return new RedirectView("/products/management/list-all");
    }

    @DeleteMapping("/management/delete")
    public RedirectView deleteProduct(@RequestParam(name = "id") int productId) {
        Product deletedProduct = productService.getProductById(productId);
        productService.deleteProduct(deletedProduct);
        return new RedirectView("/products/management/list-all");
    }

    @PutMapping("/management/update")
    public RedirectView updateProduct(@RequestParam int id,
                                      @RequestParam("name") String name,
                                      @RequestParam("price") int price,
                                      @RequestParam("stock") int stock,
                                      @RequestParam("color") String color,
                                      @RequestParam("brand") int brandId,
                                      @RequestParam("category") int categoryId,
                                      @RequestParam("description") String description) {
        Product updatedProduct = productService.getProductById(id);
        updatedProduct.setName(name);
        updatedProduct.setPrice(price);
        updatedProduct.setStock(stock);
        updatedProduct.setColor(color);
        updatedProduct.setBrand(brandService.getBrandById(brandId));
        updatedProduct.setCategory(categoryService.getCategoryById(categoryId));
        updatedProduct.setDescription(description);
        productService.saveProduct(updatedProduct);
        return new RedirectView("/products/management/list-all");
    }

}
