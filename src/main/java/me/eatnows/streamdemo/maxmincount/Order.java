package me.eatnows.streamdemo.maxmincount;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class Order {
    private long id;
    private LocalDateTime createdAt;
    private long createdByUserId;
    private OrderStatus status;
    private BigDecimal amount;
    private List<OrderLine> orderLines;

    public Order setId(long id) {
        this.id = id;
        return this;
    }

    public Order setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Order setCreatedByUserId(long createdByUserId) {
        this.createdByUserId = createdByUserId;
        return this;
    }

    public Order setStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    public Order setAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public Order setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
        return this;
    }

    public enum OrderStatus {
        CREATED,
        IN_PROGRESS,
        ERROR,
        PROCESSED
    }
}
