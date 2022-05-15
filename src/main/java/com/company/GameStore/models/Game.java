package com.company.GameStore.models;

import com.sun.istack.NotNull;

import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class Game {

    @Id
    @NotNull
    private int game_id;

    @NotNull
    @Size(max = 50, message = "Title cannot be over 50 characters.")
    private String title;

    @NotNull
    @Size(max = 50, message = "ESRB rating cannot be over 50 characters.")
    private String esrb_rating;

    @NotNull
    @Size(max = 255, message = "Description cannot be over 255 characters.")
    private String description;

    @NotNull
    private BigDecimal price;
    //    price decimal(5, 2) not null,

    @NotNull
    @Size(max = 50, message = "Studio cannot be over 50 characters.")
    private String studio;

    @NotNull
    private int quantity;

}
