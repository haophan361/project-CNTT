package com.h_ecommerce_store.Service;

import com.h_ecommerce_store.DTO.response.comment_Product;
import com.h_ecommerce_store.DTO.response.product_Rating;
import com.h_ecommerce_store.Model.Comments;
import com.h_ecommerce_store.Repository.comment_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class comment_Service
{
    @Autowired
    comment_Repository comment_repository;
    public product_Rating getRatingProduct(int productID)
    {
        product_Rating product_rating= comment_repository.getRatingProduct(productID);
        return product_rating;
    }
    public List<comment_Product> getCommentByProduct(int productID)
    {
        return comment_repository.getCommentsByProduct(productID);
    }
    public Long getNumberOfCommentsByRating(int rating,int productID)
    {
        return comment_repository.getNumberOfCommentsByRating(rating,productID);
    }
    public Comments postComment(Comments comment)
    {
        int ID=comment_repository.newID();
        comment.setID(ID);

        return comment_repository.save(comment);
    }
}
