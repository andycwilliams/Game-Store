package com.company.GameStore.models;

import com.sun.istack.NotNull;

import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class Console {

    @Id
    @NotNull
    private int console_id;

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

    @NotNull
    private BigDecimal price;
    //    price decimal(5, 2) not null,
    @NotNull
    private int quantity;
}
