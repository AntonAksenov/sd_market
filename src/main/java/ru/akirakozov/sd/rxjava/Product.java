package ru.akirakozov.sd.rxjava;

public class Product {

    String name;
    double priceInRubles;

    public Product(String name, double priceInRubles) {
        this.name = name;
        this.priceInRubles = priceInRubles;
    }

    public double price(Currency currency) {
        switch (currency) {
            case RUBLE -> {
                return priceInRubles;
            }
            case DOLLAR -> {
                return priceInRubles / 75;
            }
            case EURO -> {
                return priceInRubles / 85;
            }
        }
        throw new IllegalArgumentException("no such currency");
    }

    public String getName() {
        return name;
    }
}