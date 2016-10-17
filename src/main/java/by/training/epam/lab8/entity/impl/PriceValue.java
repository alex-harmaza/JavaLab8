package by.training.epam.lab8.entity.impl;

import by.training.epam.lab8.entity.IPrice;


public class PriceValue implements IPrice {

    private double value;


    public PriceValue(double value){
        this.value = value;
    }


    public Double getValue() {
        return value;
    }


    @Override
    public String toString() {
        return value + " руб.";
    }
}
