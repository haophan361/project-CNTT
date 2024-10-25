package com.h_ecommerce_store.DTO.request;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class postComment
{
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
