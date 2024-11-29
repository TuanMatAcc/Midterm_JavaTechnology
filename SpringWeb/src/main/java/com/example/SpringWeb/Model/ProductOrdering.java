package com.example.SpringWeb.Model;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductOrdering {
    @EmbeddedId
    private ProductOrderingKey productOrderingId;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("orderId")
    @JoinColumn(name = "order_id", nullable = false)
    private Orders order;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("productId")
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    private int quantity;

    public ProductOrdering(Orders order, Product product, int quantity) {
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.productOrderingId = new ProductOrderingKey(order.getOrderId(), product.getProductId());
    }
}
