package by.training.epam.lab8.entity.impl;

import by.training.epam.lab8.entity.IPrice;

import java.util.HashMap;
import java.util.Map;


public class PriceMap implements IPrice {

    private Map<String, Double> positions;


    public PriceMap(){
        positions = new HashMap<>();
    }


    public Map<String, Double> getValue() {
        return positions;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String name : positions.keySet()){
            builder.append(name).append(" ").append(positions.get(name)).append(" руб.; ");
        }
        return builder.toString();
    }
}
