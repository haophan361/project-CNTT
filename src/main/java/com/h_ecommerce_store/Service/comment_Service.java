package com.h_ecommerce_store.Service;

import com.h_ecommerce_store.DTO.response.comment_Product;
import com.h_ecommerce_store.DTO.response.product_Rating;
import com.h_ecommerce_store.Entity.Comments;
import com.h_ecommerce_store.Repository.comment_Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
public class comment_Service
{
    private final comment_Repository comment_repository;
    public product_Rating getRatingProduct(int productID)
    {
        return comment_repository.getRatingProduct(productID);
    }
    public List<comment_Product> getCommentByProduct(int productID)
    {
        return comment_repository.getCommentsByProduct(productID);
    }
    public Long getNumberOfCommentsByRating(int rating,int productID)
    {
        return comment_repository.getNumberOfCommentsByRating(rating,productID);
    }
    public void postComment(Comments comment)
    {
        int ID=comment_repository.newID();
        comment.setID(ID);
        comment_repository.save(comment);
    }
    public Long getNumberOfComments(String email,int productID)
    {
        return comment_repository.getNumberOfComments(email,productID);
    }
}
