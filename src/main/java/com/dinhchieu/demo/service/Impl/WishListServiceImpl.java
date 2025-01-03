package com.dinhchieu.demo.service.Impl;

import com.dinhchieu.demo.dao.ProductRepository;
import com.dinhchieu.demo.dao.UserRepository;
import com.dinhchieu.demo.dto.response.WishListProductResponseDTO;
import com.dinhchieu.demo.entity.Product;
import com.dinhchieu.demo.entity.User;
import com.dinhchieu.demo.handle.ProductNotFoundException;
import com.dinhchieu.demo.handle.UserNotFoundException;
import com.dinhchieu.demo.service.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishListServiceImpl implements WishListService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;


    private WishListProductResponseDTO mapToWishListProductResponseDTO(Product product) {
        WishListProductResponseDTO wishListProductResponseDTO = new WishListProductResponseDTO();
        wishListProductResponseDTO.setId(product.getId());
        wishListProductResponseDTO.setName(product.getName());
        return wishListProductResponseDTO;
    }

    @Override
    public void addProductToWishList(int userId, int productId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + userId + " is not found!");
        }

        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product with id " + productId + " is not found!");
        }

        if(user.get().getWishList().contains(product.get())){
            throw  new ProductNotFoundException("Product with id " + productId + " was existed in user " + user.get().getUsername() + " wishlist!" );
        }

        user.get().getWishList().add(product.get());
        userRepository.save(user.get());
    }

    @Override
    public void removeProductFromWishList(int userId, int productId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + userId + " is not found!");
        }

        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            throw new ProductNotFoundException("Product with id " + productId + " is not found!");
        }

        if(!user.get().getWishList().contains(product.get())){
            throw new ProductNotFoundException("Product with id " + productId + " was not existed in user " + user.get().getUsername() + " wishlist!" );
        }

        user.get().getWishList().remove(product.get());
        userRepository.save(user.get());
    }

    @Override
    public List<WishListProductResponseDTO> getWishListByUserId(int userId) {

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with id " + userId + " is not found!");
        }

        return user.get().getWishList().stream().map(this::mapToWishListProductResponseDTO).toList();
    }
}
