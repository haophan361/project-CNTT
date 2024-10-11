package com.h_ecommerce_store.DTO.request;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class postComment
{
    private String comment;
    private int productID;
    @Min(value =1,message ="Đánh giá không được để trống")
    private int rate;
    public postComment(int productID)
    {
        this.productID = productID;
    }
}
