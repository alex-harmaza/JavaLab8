package by.training.epam.lab8.entity.impl;


public enum DishType {

    COLD_SNACKS("Холодные закуски"),
    HOT_SNACKS("Горячие закуски"),
    SALADS("Салаты"),
    BREAKFAST("Завтраки"),
    SOUPS("Супы"),
    FISH_DISHES("Рыбные блюда"),
    MEAT_DISHES("Мясные блюда"),
    GARNISH("Гарниры"),
    DISHES_ON_THE_GRILL("Блюда на мангале"),
    FROM_THE_CHEF("От шеф-повара"),
    THE_APP("Приложение"),
    DESSERT("Дессерт");


    public static DishType getTypeByTagValue(String value){
        DishType result = null;
        for (DishType typeEnum : DishType.values()){
            if (typeEnum.value.equals(value)){
                result = typeEnum;
                break;
            }
        }
        return result;
    }


    private String value;


    DishType(String value){
        this.value = value;
    }


    @Override
    public String toString() {
        return this.value;
    }
}
