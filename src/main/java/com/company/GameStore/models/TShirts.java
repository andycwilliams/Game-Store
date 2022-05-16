package com.company.GameStore.models;

import com.sun.istack.NotNull;

import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

public class TShirts {

    @Id
    @NotNull
    private int t_shirt_id;

    @NotNull
    @Size(max = 20, message = "Size cannot be over 50 characters.")
    private String size;

    @NotNull
    @Size(max = 20, message = "Color cannot be over 50 characters.")
    private String color;

    @NotNull
    @Size(max = 255, message = "Description cannot be over 255 characters.")
    private String description;

    @NotNull
    private BigDecimal price;

    @NotNull
    private int quantity;

    public TShirts(int t_shirt_id, String size, String color, String description, BigDecimal price, int quantity) {
        this.t_shirt_id = t_shirt_id;
        this.size = size;
        this.color = color;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public TShirts(String size, String color, String description, BigDecimal price, int quantity) {
        this.size = size;
        this.color = color;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public TShirts() {
    }

    public int getT_shirt_id() {
        return t_shirt_id;
    }

    public void setT_shirt_id(int t_shirt_id) {
        this.t_shirt_id = t_shirt_id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TShirts tShirts = (TShirts) o;
        return t_shirt_id == tShirts.t_shirt_id && quantity == tShirts.quantity && Objects.equals(size, tShirts.size) && Objects.equals(color, tShirts.color) && Objects.equals(description, tShirts.description) && Objects.equals(price, tShirts.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(t_shirt_id, size, color, description, price, quantity);
    }

    @Override
    public String toString() {
        return "TShirts{" +
                "t_shirt_id=" + t_shirt_id +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
