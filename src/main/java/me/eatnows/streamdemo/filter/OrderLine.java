package me.eatnows.streamdemo.filter;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class OrderLine {
    private long id;
    private OrderLineType type;
    private long productId;
    private int quantity;
    private BigDecimal amount;

    public OrderLine setId(long id) {
        this.id = id;
        return this;
    }

    public OrderLine setType(OrderLineType type) {
        this.type = type;
        return this;
    }

    public OrderLine setProductId(long productId) {
        this.productId = productId;
        return this;
    }

    public OrderLine setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public OrderLine setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public enum OrderLineType {
        PURCHASE,
        DISCOUNT
    }
}
