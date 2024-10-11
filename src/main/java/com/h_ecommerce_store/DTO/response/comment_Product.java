package com.h_ecommerce_store.DTO.response;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
public class comment_Product
{
    private String comment;
    private String name;
    private LocalDateTime dateTime;
    private int rate;
    private String formattedDateTime;
    private static  final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    public comment_Product(String comment, String name, LocalDateTime dateTime,int rate)
    {
        this.comment = comment;
        this.name = name;
        this.dateTime = dateTime;
        this.rate = rate;
        this.formattedDateTime = dateTime.format(formatter);
    }
    public comment_Product()
    {

    }
}
