package me.eatnows.streamdemo.service;

import me.eatnows.streamdemo.designpattern.Price;

public class TaxPriceProcessor implements PriceProcessor{
    @Override
    public Price process(Price price) {
        return new Price(price.getPrice() + ", then applied tax");
    }
}
