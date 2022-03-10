package me.eatnows.streamdemo.service;

import me.eatnows.streamdemo.designpattern.Price;

public class DiscountPriceProcessor implements PriceProcessor{
    @Override
    public Price process(Price price) {
        return new Price(price.getPrice() + ", then applied discount");
    }
}
