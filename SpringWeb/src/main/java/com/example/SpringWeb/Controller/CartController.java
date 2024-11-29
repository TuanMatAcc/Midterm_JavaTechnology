package com.example.SpringWeb.Controller;

import com.example.SpringWeb.Model.CartItem;
import com.example.SpringWeb.Model.Product;
import com.example.SpringWeb.Service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ProductService productService;
    private static final String CART_COOKIE_NAME = "shopping_cart";
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Get cart from cookie
    public List<CartItem> getCart(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (CART_COOKIE_NAME.equals(cookie.getName())) {
                    try {
                        String decodedCart = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                        List<CartItem> cartItems = objectMapper.readValue(decodedCart, new TypeReference<List<CartItem>>() {});
                        return cartItems;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                        e.printStackTrace();
                        return new ArrayList<>();
                    }
                }
            }
        }

        return new ArrayList<>();
    }

    public void removeCookie() {
        Cookie cookie = new Cookie(CART_COOKIE_NAME, "");
        cookie.setMaxAge(0);
    }

    // Save cart to cookie
    public Cookie saveCart(HttpServletResponse response, List<CartItem> cart) {
        try {
            String cartJson = objectMapper.writeValueAsString(cart);
            cartJson = URLEncoder.encode(cartJson, StandardCharsets.UTF_8);
            Cookie cartCookie = new Cookie(CART_COOKIE_NAME, cartJson);
            cartCookie.setPath("/");
            cartCookie.setHttpOnly(true);
            cartCookie.setMaxAge(24 * 60 * 60);
            response.addCookie(cartCookie);
            return cartCookie;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update item in cart
    @PostMapping("/add")
    @ResponseBody
    public void addToCart(HttpServletRequest request,HttpServletResponse response, @RequestParam(name = "id")Integer productId) {
        List<CartItem> cart = getCart(request);
        if(increaseCartItem(request, response, productId)) {
            return;
        }
        // Add new item to cart
        CartItem cartItem = new CartItem(productService.getProductById(productId), 1, productService.getProductById(productId).getPrice());
        cart.add(cartItem);
        System.out.println(saveCart(response, cart).getValue());

    }

    @DeleteMapping("/remove")
    @ResponseBody
    public void removeCart(HttpServletRequest request,HttpServletResponse response, @RequestParam(name = "id")Integer productId) {
        List<CartItem> cart = getCart(request);
        for (CartItem item : cart) {
            if (item.getProduct().getProductId() == productId) {
                System.out.println(item.getProduct().getProductId());
                cart.remove(item);
                saveCart(response, cart);
                System.out.println("Delete: " + cart.toString());
                return;
            }
        }
    }

    @PutMapping("/update/decrease")
    @ResponseBody
    public boolean decreaseCartItem(HttpServletRequest request,HttpServletResponse response, @RequestParam(name = "id")Integer productId) {
        List<CartItem> cart = getCart(request);
        for (CartItem item : cart) {
            if (item.getProduct().getProductId() == productId) {
                item.setQuantity(item.getQuantity()-1);
                saveCart(response, cart);
                return true;
            }
        }
        return false;
    }

    @PutMapping("/update/increase")
    @ResponseBody
    public boolean increaseCartItem(HttpServletRequest request,HttpServletResponse response, @RequestParam(name = "id")Integer productId) {
        List<CartItem> cart = getCart(request);
        for (CartItem item : cart) {
            if (item.getProduct().getProductId() == productId) {
                item.setQuantity(item.getQuantity()+1);
                saveCart(response, cart);
                return true;
            }
        }
        return false;
    }

    public void clearCart(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (CART_COOKIE_NAME.equals(cookie.getName())) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    return;
                }
            }
        }
    }

    @GetMapping("/list")
    public String showCart( @ModelAttribute("errorMessage") String errorMessage,
                            @ModelAttribute("resultMessage") String successMessage,
                            HttpServletRequest request,
                            Model model) {
//        Product productTest =  productService.getProductById(3);
//        CartItem cartItemTest = new CartItem(productTest, 1, productTest.getPrice());
        List<CartItem> lstCart = getCart(request);
        System.out.println(lstCart.toString());
//        lstCart.add(cartItemTest);
        model.addAttribute("shoppingCart", lstCart);
        if(errorMessage == null || errorMessage.isEmpty()) {
            model.asMap().remove("errorMessage");
        }
        if(successMessage == null || successMessage.isEmpty()) {
            model.asMap().remove("resultMessage");
        }
        return "cart";
    }
}
