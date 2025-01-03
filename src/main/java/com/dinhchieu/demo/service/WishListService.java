package com.dinhchieu.demo.service;

import com.dinhchieu.demo.dto.response.WishListProductResponseDTO;

import java.util.List;

public interface WishListService {
    void addProductToWishList(int userId, int productId);
    void removeProductFromWishList(int userId, int productId);
    List<WishListProductResponseDTO> getWishListByUserId(int userId);
}
