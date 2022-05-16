package com.company.GameStore.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import javax.persistence.*;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "console")
public class Console {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int console_id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "albumId")
    private Set<Games> games = new HashSet<>();

    @NotNull
    @Size(max = 50, message = "Model cannot be over 50 characters.")
    private String model;

    @NotNull
    @Size(max = 50, message = "Manufacturer cannot be over 50 characters.")
    private String manufacturer;

    @Size(max = 20, message = "Memory amount cannot be over 255 characters.")
    private String memory_amount;

    @Size(max = 20, message = "Processor cannot be over 255 characters.")
    private String processor;

    @PositiveOrZero
    private BigDecimal price;

    @PositiveOrZero
    private int quantity;

    public Console() {
    }

    public Console(int console_id, Set<Games> games, String model, String manufacturer, String memory_amount, String processor, BigDecimal price, int quantity) {
        this.console_id = console_id;
        this.games = games;
        this.model = model;
        this.manufacturer = manufacturer;
        this.memory_amount = memory_amount;
        this.processor = processor;
        this.price = price;
        this.quantity = quantity;
    }

    public Console(Set<Games> games, String model, String manufacturer, String memory_amount, String processor, BigDecimal price, int quantity) {
        this.games = games;
        this.model = model;
        this.manufacturer = manufacturer;
        this.memory_amount = memory_amount;
        this.processor = processor;
        this.price = price;
        this.quantity = quantity;
    }

    public int getConsole_id() {
        return console_id;
    }

    public void setConsole_id(int console_id) {
        this.console_id = console_id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getMemory_amount() {
        return memory_amount;
    }

    public void setMemory_amount(String memory_amount) {
        this.memory_amount = memory_amount;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
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
        Console console = (Console) o;
        return console_id == console.console_id && quantity == console.quantity && Objects.equals(model, console.model) && Objects.equals(manufacturer, console.manufacturer) && Objects.equals(memory_amount, console.memory_amount) && Objects.equals(processor, console.processor) && Objects.equals(price, console.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(console_id, model, manufacturer, memory_amount, processor, price, quantity);
    }

    @Override
    public String toString() {
        return "Console{" +
                "console_id=" + console_id +
                ", model='" + model + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", memory_amount='" + memory_amount + '\'' +
                ", processor='" + processor + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
