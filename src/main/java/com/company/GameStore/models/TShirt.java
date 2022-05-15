package com.company.GameStore.models;

import com.sun.istack.NotNull;

import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class TShirt {

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
    //    price decimal(5, 2) not null,

    @NotNull
    private int quantity;

}
