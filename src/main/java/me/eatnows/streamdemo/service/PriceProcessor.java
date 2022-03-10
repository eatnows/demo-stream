package me.eatnows.streamdemo.service;

import me.eatnows.streamdemo.designpattern.Price;

@FunctionalInterface
public interface PriceProcessor {
    Price process(Price price);

    default PriceProcessor andThen(PriceProcessor next) {
        return price -> next.process(process(price));
    }
}
