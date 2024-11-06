package com.h_ecommerce_store.DTO.request;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class postComment
{
    @Size(max = 500, message = "Số ký tự tối đa là 500")
    private String comment;
    private int productID;
    private Long allowCommentTime;
    private int rate;
    public postComment(int productID,Long allowCommentTime)
    {
        this.productID = productID;
        this.allowCommentTime = allowCommentTime;
    }
    public postComment()
    {

    }
}
