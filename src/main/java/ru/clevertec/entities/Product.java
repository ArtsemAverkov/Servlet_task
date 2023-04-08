package ru.clevertec.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
}
