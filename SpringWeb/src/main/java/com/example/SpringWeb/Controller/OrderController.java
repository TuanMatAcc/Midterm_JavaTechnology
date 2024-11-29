package com.example.SpringWeb.Controller;

import com.example.SpringWeb.Model.Orders;
import com.example.SpringWeb.Model.Product;
import com.example.SpringWeb.Model.ProductOrdering;
import com.example.SpringWeb.Service.OrderService;
import com.example.SpringWeb.Service.ProductOrderingService;
import com.example.SpringWeb.Service.ProductService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    private static final String CART_COOKIE_NAME = "shopping_cart";
    private static final String DEFAULT = "ALL";
    private static final String PENDING = "PENDING";
    private static final String SHIPPED = "SHIPPED";
    private static final String DELIVERED = "DELIVERED";
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductOrderingService productOrderingService;
    @Autowired
    private ProductService productService;

    @PostMapping("/add")
    public String addOrder(@RequestParam(value = "name-customer", required = false) String name,
                           @RequestParam(value = "phone-customer", required = false) String phone,
                           @RequestParam(value = "address-customer", required = false) String address,
                           @RequestParam(value = "productids-quantity", required = false) String[] orderedProducts,
                           HttpServletRequest request,
                           HttpServletResponse response,
                           RedirectAttributes redirectAttributes) {

        Orders order = new Orders();
        order.setName(name);
        order.setPhone(phone);
        order.setAddress(address);
        // Store the order
        try {
            orderService.saveOrder(order);
        }
        catch (IllegalArgumentException e) {
            // Pass the error message to the view
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/cart/list";
        }


        List<ProductOrdering> productsOrdering = new ArrayList<>();
        for(String content : orderedProducts) {
            int id = Integer.parseInt(content.split(" ")[0]);
            int quantity = Integer.parseInt(content.split(" ")[1]);

            productsOrdering.add(new ProductOrdering(order, productService.getProductById(id), quantity));
        }
        // Store content of the order and update stock
        try {
            productOrderingService.saveProductOrdering(productsOrdering);
        }
        catch (IllegalArgumentException e) {
            // Pass the error message to the view
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/cart/list";
        }
        // Store content into the order
        order.setProducts(productsOrdering);
        orderService.saveOrder(order);

        // Remove the cart cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // Check if the cookie name matches the one you want to remove
                if (cookie.getName().equals(CART_COOKIE_NAME)) {
                    // Set the max age to 0 to delete the cookie
                    cookie.setMaxAge(0);
                    cookie.setPath("/"); // Match original path
                    response.addCookie(cookie);
                    // Add success message to the model
                    break;
                }
            }
        }
        redirectAttributes.addFlashAttribute("resultMessage", "Order successfully completed!");
        return "redirect:/cart/list";
    }

    @GetMapping("/management/list-all")
    public String listAllProducts( @RequestParam (defaultValue = "0") int page,
                                   @RequestParam (defaultValue = "6") int size,
                                   @RequestParam (defaultValue = "ALL") String status,
                                   Model model) {
        System.out.println("status: " + status);
        if(status.equals(DEFAULT)) {
            Page<Orders> orders = orderService.getAllOrders(page, size);
            model.addAttribute("orders", orders.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", orders.getTotalPages());
        }
        else if(status.equals(PENDING)) {
            Page<Orders> orders = orderService.getAllPendingOrders(page, size);
            model.addAttribute("orders", orders.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", orders.getTotalPages());
        }
        else if(status.equals(SHIPPED)) {
            Page<Orders> orders = orderService.getAllShippedOrders(page, size);
            model.addAttribute("orders", orders.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", orders.getTotalPages());
        }
        else if(status.equals(DELIVERED)) {
            Page<Orders> orders = orderService.getAllDeliveredOrders(page, size);
            model.addAttribute("orders", orders.getContent());
            model.addAttribute("currentPage", page);
            model.addAttribute("totalPages", orders.getTotalPages());
        }
        if(!status.equals(DEFAULT)) {
            model.addAttribute("selectedStatus", status);
        }
        return "order-management";
    }

    @PutMapping("/management/update-status")
    public RedirectView updateStatus(@RequestParam("id") int orderId,
                               @RequestParam("current-status") String status) {
        Orders order = orderService.getOrderById(orderId);
        System.out.println(status + " is status");
        // Undefined case
        if(order == null) {
            return new RedirectView( "/order/management/list-all");
        }
        if(status.equals(PENDING)) {
            System.out.println("in pending");
            order.setStatus(Orders.orderStatus.SHIPPED);
        }
        else if(status.equals(SHIPPED)) {
            order.setStatus(Orders.orderStatus.DELIVERED);
        }
        orderService.saveOrder(order);

        return new RedirectView( "/order/management/list-all");
    }

    @DeleteMapping("/management/delete")
    public RedirectView deleteOrder(@RequestParam("id") int orderId) {
        Orders order = orderService.getOrderById(orderId);
        // Undefined case
        if(order == null) {
            return new RedirectView( "/order/management/list-all");
        }
        order.setDeleted(true);
        orderService.saveOrder(order);
        return new RedirectView( "/order/management/list-all");
    }

}

