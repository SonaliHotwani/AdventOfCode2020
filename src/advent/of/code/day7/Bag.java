package advent.of.code.day7;

import java.util.List;
import java.util.Objects;

public class Bag {
    String color;
    Integer quantity;
    List<Bag> content;

    Bag(String color, Integer quantity, List<Bag> content) {
        this.color = color;
        this.quantity = quantity;
        this.content = content;
    }

    Bag(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bag bag = (Bag) o;
        return color.equals(bag.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color);
    }

    void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    void setContent(List<Bag> content) {
        this.content = content;
    }
}
