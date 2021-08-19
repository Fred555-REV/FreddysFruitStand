package fruit.stand.products;

import java.util.Date;

public class Fruit extends Product {
    //    private List<String> typeList;
    private String color;

    public Fruit(String name, String subtype, Date expDate, double cost, int amount, String color) {
        super(name, subtype, expDate, cost, amount);
        this.color = color;
    }

    @Override
    public String toString() {
        return "\nFruit{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", color='" + color + '\'' +
                ", expDate=" + expDate +
                ", cost=" + cost +
                ", amount=" + amount +
                '}';
    }
}
