package me.eatnows.streamdemo;

import me.eatnows.streamdemo.designpattern.Price;
import me.eatnows.streamdemo.designpattern.User;
import me.eatnows.streamdemo.service.BasicPriceProcessor;
import me.eatnows.streamdemo.service.DiscountPriceProcessor;
import me.eatnows.streamdemo.service.PriceProcessor;
import me.eatnows.streamdemo.service.TaxPriceProcessor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DesignPatternTest {

    @Test
    @DisplayName("Builder Pattern")
    void builderPatternTest() {
//        User user = User.builder(1, "Apple")
//                .withEmailAddress("apple@email.com")
//                .withVerified(true)
//                .build();
//
//        System.out.println(user);

        User user = User.builder(1, "Apple")
                .with(builder -> {
                    builder.emailAddress = "apple@email.com";
                    builder.isVerified = true;
                }).build();

        System.out.println(user);
    }

    @Test
    @DisplayName("Decorator Pattern")
    void decoratorPatternTest() {
        Price unprocessedPrice = new Price("Original Price");

        PriceProcessor basicPriceProcessor = new BasicPriceProcessor();
        PriceProcessor discountPriceProcessor = new DiscountPriceProcessor();
        PriceProcessor taxPriceProcessor = new TaxPriceProcessor();

        PriceProcessor decoratedPriceProcessor = basicPriceProcessor
                .andThen(discountPriceProcessor)
                .andThen(taxPriceProcessor);
        Price processedPrice = decoratedPriceProcessor.process(unprocessedPrice);
        System.out.println(processedPrice.getPrice());

        PriceProcessor decoratedPriceProcessor2 = basicPriceProcessor
                .andThen(taxPriceProcessor)
                .andThen(price -> new Price(price.getPrice() + ", then apply another procedure"));
        Price processedPrice2 = decoratedPriceProcessor2.process(unprocessedPrice);
        System.out.println(processedPrice2.getPrice());
    }
}
