package me.eatnows.streamdemo;

import me.eatnows.streamdemo.composition.Order;
import me.eatnows.streamdemo.composition.OrderLine;
import me.eatnows.streamdemo.designpattern.Price;
import me.eatnows.streamdemo.designpattern.User;
import me.eatnows.streamdemo.service.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

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

    @Test
    @DisplayName("Strategy Pattern")
    void strategyPatternTest() {
        User user1 = User.builder(1, "Apple")
                .with(builder -> {
                    builder.emailAddress = "apple@email.com";
                    builder.isVerified = false;
                    builder.friendUserIds = Arrays.asList(201, 202, 203, 204, 211, 212, 213, 214);
                }).build();
        User user2 = User.builder(1, "Kiwi")
                .with(builder -> {
                    builder.emailAddress = "kiwi@email.com";
                    builder.isVerified = true;
                    builder.friendUserIds = Arrays.asList(212, 213, 214);
                }).build();
        User user3 = User.builder(1, "Banana")
                .with(builder -> {
                    builder.emailAddress = "Banana@email.com";
                    builder.isVerified = true;
                    builder.friendUserIds = Arrays.asList(201, 202, 203, 204, 211, 212);
                }).build();
        List<User> users = Arrays.asList(user1, user2, user3);

        EmailSender emailSender = new EmailSender();
        EmailProvider verifyYourEmailAddressEmailProvider = new VerifyYourEmailAddressEmailProvider();
        EmailProvider makeMoreFriendsEmailProvider = new MakeMoreFriendsEmailProvider();

        emailSender.setEmailProvider(verifyYourEmailAddressEmailProvider);
        users.stream()
                .filter(user -> !user.isVerified())
                .forEach(emailSender::sendEmail);

        emailSender.setEmailProvider(makeMoreFriendsEmailProvider);
        users.stream()
                .filter(user -> user.isVerified())
                .filter(user -> user.getFriendUserIds().size() <= 5)
                .forEach(emailSender::sendEmail);

        // 전략을 클래스로 정의해주는게 아니라 바로 만들수도 있다.
        // EmailProvider는 functional interface 이다.
        emailSender.setEmailProvider(user -> "'Play With Friends' email for " + user.getName());
        users.stream()
                .filter(User::isVerified)
                .filter(user -> user.getFriendUserIds().size() > 5)
                .forEach(emailSender::sendEmail);
    }

    @Test
    @DisplayName("Template Method Pattern1")
    void templateMethodPatternTest1() {
        User user1 = User.builder(1, "Apple")
                .with(builder -> {
//                    builder.emailAddress = "apple@email.com";
                    builder.isVerified = false;
                    builder.friendUserIds = Arrays.asList(201, 202, 203, 204, 211, 212, 213, 214);
                }).build();

        UserService userService = new UserService();
        InternalUserService internalUserService = new InternalUserService();

//        userService.createUser(user1);
        internalUserService.createUser(user1);
    }

    @Test
    @DisplayName("Template Method Pattern2")
    void templateMethodPatternTest2() {
        // 클래스를 만들지 않고 함수형 프로그래밍을 이용하여 템플릿을 만듦
        User user1 = User.builder(1, "Apple")
                .with(builder -> {
                    builder.emailAddress = "apple@email.com";
                    builder.isVerified = false;
                    builder.friendUserIds = Arrays.asList(201, 202, 203, 204, 211, 212, 213, 214);
                }).build();

        UserServiceFunctionalWay userServiceFunctionalWay = new UserServiceFunctionalWay(
                user -> {
                    System.out.println("Validating user " + user.getName());
                    return user.getName() != null && user.getEmailAddress().isPresent();
                },
                user -> {
                    System.out.println("Writing user " + user.getName() + " to DB");
                }
        );
        userServiceFunctionalWay.createUser(user1);
    }

    @Test
    @DisplayName("Chain of Responsibility Pattern")
    public void chainOfResponsibilityPatternTest() {
        OrderProcessStep initializeStep = new OrderProcessStep(order -> {
            if (order.getStatus() == Order.OrderStatus.CREATED) {
                System.out.println("Start processing order " + order.getId());
                order.setStatus(Order.OrderStatus.IN_PROGRESS);
            }
        });

        OrderProcessStep setOrderAmountStep = new OrderProcessStep(order -> {
            if (order.getStatus() == Order.OrderStatus.IN_PROGRESS) {
                System.out.println("Setting amount of order " + order.getId());
                order.setAmount(order.getOrderLines().stream().map(OrderLine::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add));
            }
        });

        OrderProcessStep verifyOrderStep = new OrderProcessStep(order -> {
            if (order.getStatus() == Order.OrderStatus.IN_PROGRESS) {
                System.out.println("Verifying order " + order.getId());
                if (order.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
                    order.setStatus(Order.OrderStatus.ERROR);
                }
            }
        });

        OrderProcessStep processPaymentStep = new OrderProcessStep(order -> {
            if (order.getStatus() == Order.OrderStatus.IN_PROGRESS) {
                System.out.println("Processing payment of order " + order.getId());
                order.setStatus(Order.OrderStatus.PROCESSED);
            }
        });

        OrderProcessStep handleErrorStep = new OrderProcessStep(order -> {
            if (order.getStatus() == Order.OrderStatus.ERROR) {
                System.out.println("Sending out 'Faild to process order' alert for order " + order.getId());
            }
        });

        OrderProcessStep completeProcessingOrderStep = new OrderProcessStep(order -> {
            if (order.getStatus() == Order.OrderStatus.PROCESSED) {
                System.out.println("Finished processing order " + order.getId());
            }
        });

        OrderProcessStep chainedOrderProcessSteps = initializeStep
                .setNext(setOrderAmountStep)
                .setNext(verifyOrderStep)
                .setNext(processPaymentStep)
                .setNext(handleErrorStep)
                .setNext(completeProcessingOrderStep);

        Order order = new Order()
                .setId(1001)
                .setStatus(Order.OrderStatus.CREATED)
                .setOrderLines(Arrays.asList(new OrderLine().setAmount(BigDecimal.valueOf(1000)),
                        new OrderLine().setAmount(BigDecimal.valueOf(2000))));

//        chainedOrderProcessSteps.process(order);

        // error 발생
        Order failingOrder = new Order()
                .setId(1002)
                .setStatus(Order.OrderStatus.CREATED)
                .setOrderLines(Arrays.asList(new OrderLine().setAmount(BigDecimal.valueOf(1000)),
                        new OrderLine().setAmount(BigDecimal.valueOf(-2000))));
        chainedOrderProcessSteps.process(failingOrder);
    }
}
