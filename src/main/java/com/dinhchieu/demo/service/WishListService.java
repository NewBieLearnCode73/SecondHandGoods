package com.dinhchieu.demo.service;

import com.dinhchieu.demo.dto.response.ProductDetailResponseDTO;

import java.util.List;

public interface WishListService {
    void addProductToWishList(int userId, int productId);
    void removeProductFromWishList(int userId, int productId);
    List<ProductDetailResponseDTO> getWishListByUserId(int userId);
}
