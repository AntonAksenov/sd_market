package ru.akirakozov.sd.rxjava;

public class Account {
    long id;
    Currency currency;

    public Account(long id, Currency currency) {
        this.id = id;
        this.currency = currency;
    }

    public Currency getCurrency() {
        return currency;
    }
}
