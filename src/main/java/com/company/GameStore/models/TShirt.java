package com.company.GameStore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "t_shirt")
public class TShirt {

    @Id
    @NotNull
    @Column(name = "t_shirt_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int tShirtId;

    @NotNull
    @Size(max = 20, message = "Size cannot be over 50 characters.")
    private String size;

    @NotNull
    @Size(max = 20, message = "Color cannot be over 50 characters.")
    private String color;

    @NotNull
    @Size(max = 255, message = "Description cannot be over 255 characters.")
    private String description;

    @PositiveOrZero
    private BigDecimal price;

    @PositiveOrZero
    private int quantity;

    public TShirt(int tShirtId, String size, String color, String description, BigDecimal price, int quantity) {
        this.tShirtId = tShirtId;
        this.size = size;
        this.color = color;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public TShirt(String size, String color, String description, BigDecimal price, int quantity) {
        this.size = size;
        this.color = color;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public TShirt() {
    }

    public int gettShirtId() {
        return tShirtId;
    }

    public void settShirtId(int tShirtId) {
        this.tShirtId = tShirtId;
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
        TShirt tShirt = (TShirt) o;
        return tShirtId == tShirt.tShirtId && quantity == tShirt.quantity && Objects.equals(size, tShirt.size) && Objects.equals(color, tShirt.color) && Objects.equals(description, tShirt.description) && Objects.equals(price, tShirt.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tShirtId, size, color, description, price, quantity);
    }

    @Override
    public String toString() {
        return "TShirts{" +
                "tShirtId=" + tShirtId +
                ", size='" + size + '\'' +
                ", color='" + color + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
