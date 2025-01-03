package com.dinhchieu.demo.controller;

import com.dinhchieu.demo.dto.request.WishListRequestDTO;
import com.dinhchieu.demo.dto.request.WishListRequestRemoveDTO;
import com.dinhchieu.demo.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WishListController {
    @Autowired
    private WishListService wishListService;

    @GetMapping("/api/v1/wishList/{id}")
    public ResponseEntity<?> getWishListByUserId(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK.value()).body(wishListService.getWishListByUserId(id));
    }

    @PostMapping("/api/v1/wishList")
    public ResponseEntity<?> addProductToWishList(@RequestBody WishListRequestDTO wishListRequestDTO) {
        wishListService.addProductToWishList(wishListRequestDTO.getUserId(), wishListRequestDTO.getProductId());
        return ResponseEntity.status(HttpStatus.OK.value()).body("Product added to wish list successfully!");
    }

    @DeleteMapping("/api/v1/wishList/{id}")
    public ResponseEntity<?> removeProductFromWishList( @PathVariable int id, @RequestBody WishListRequestRemoveDTO wishListRequestRemoveDTO) {
        wishListService.removeProductFromWishList(id, wishListRequestRemoveDTO.getProductId());
        return ResponseEntity.status(HttpStatus.OK.value()).body("Product removed from wish list successfully!");
    }
}
