package by.training.epam.lab8.entity.impl;

import by.training.epam.lab8.entity.IPrice;

import java.net.URL;


public class Dish {

    private String id;
    private URL image;
    private String name;
    private String description;
    private String portion;
    private IPrice price;
    private DishType type;


    public Dish(){}


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public URL getImage() {
        return image;
    }

    public void setImage(URL image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPortion() {
        return portion;
    }

    public void setPortion(String portion) {
        this.portion = portion;
    }

    public IPrice getPrice() {
        return price;
    }

    public void setPrice(IPrice price) {
        this.price = price;
    }

    public DishType getType() {
        return type;
    }

    public void setType(DishType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (id != null){
            builder.append("[").append(id).append("] ");
        }
        if (name != null){
            builder.append(name.toUpperCase()).append("\n");
        }
        if (image != null){
            builder.append("image: ").append(image).append("\n");
        }
        if (description != null){
            builder.append("description: ").append(description).append("\n");
        }
        if (portion != null){
            builder.append("portion: ").append(portion).append("\n");
        }
        if (price != null){
            builder.append("price: ").append(price).append("\n");
        }
        if (type != null){
            builder.append("type: ").append(type).append("\n");
        }
        return builder.toString();
    }
}
