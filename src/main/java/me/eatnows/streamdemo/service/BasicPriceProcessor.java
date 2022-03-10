package me.eatnows.streamdemo.service;

import me.eatnows.streamdemo.designpattern.Price;

public class BasicPriceProcessor implements PriceProcessor{

    @Override
    public Price process(Price price) {
        return price;
    }
}
