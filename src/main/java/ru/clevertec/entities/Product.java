package ru.clevertec.entities;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
public class Product{
    private Long id;
    private String name;
    private  double price;
    private  long amount;
    private double sum;
    boolean isDiscount;

    public Product(String name, double price, long amount, boolean isDiscount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.isDiscount = isDiscount;
    }

    public Product(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public boolean isDiscount() {
        return isDiscount;
    }
    public void setDiscount(boolean discount) {
        isDiscount = discount;
    }
}
