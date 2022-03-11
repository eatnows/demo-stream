package me.eatnows.streamdemo.service;

import me.eatnows.streamdemo.composition.Order;

import java.util.Optional;
import java.util.function.Consumer;

public class OrderProcessStep {
    private final Consumer<Order> processOrder;
    private OrderProcessStep next;

    public OrderProcessStep(Consumer<Order> processOrder) {
        this.processOrder = processOrder;
    }

    public OrderProcessStep setNext(OrderProcessStep next) {
        // linkedList 라고 생각하면 됨.
        // 맨 마지막에 OrderProcessStep을 추가
        if (this.next == null) {
            this.next = next;
        } else {
            this.next.setNext(next);
        }
        return this;
    }

    public void process(Order order) {
        processOrder.accept(order);
        Optional.ofNullable(next)
                .ifPresent(nextStep -> nextStep.process(order));
    }
}
