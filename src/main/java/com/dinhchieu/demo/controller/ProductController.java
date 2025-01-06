package com.dinhchieu.demo.controller;

import com.dinhchieu.demo.dto.request.ProductRequestDTO;
import com.dinhchieu.demo.dto.request.ProductStateUpdateRequestDTO;
import com.dinhchieu.demo.dto.response.PaginationResponseDTO;
import com.dinhchieu.demo.dto.response.ProductDetailResponseDTO;
import com.dinhchieu.demo.service.ImageService;
import com.dinhchieu.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/api/v1/products")
    public ResponseEntity<?> showAllProducts(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy)
    {
        PaginationResponseDTO<ProductDetailResponseDTO> response = productService.showAllProducts(pageNo, pageSize, sortBy);
        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }

    @GetMapping("/api/v1/products/user/{userId}")
    public ResponseEntity<?> showAllProductsByUserId(
            @PathVariable int userId,
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(defaultValue = "id") String sortBy)
    {
        PaginationResponseDTO<ProductDetailResponseDTO> response = productService.showAllProductsByUserId(userId, pageNo, pageSize, sortBy);
        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }

    @GetMapping("/api/v1/products/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id) throws Exception {
        Optional<ProductDetailResponseDTO> productDetailResponseDTO =  productService.getProductById(id);
        return ResponseEntity.status(HttpStatus.OK.value()).body(productDetailResponseDTO);
    }

    @PostMapping("/api/v1/products")
    public ResponseEntity<?> addProduct(@RequestBody ProductRequestDTO productRequestDTO){
        ProductDetailResponseDTO productDetailResponseDTO = productService.addProduct(productRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED.value()).body(productDetailResponseDTO);
    }

    @DeleteMapping("/api/v1/products/{id}")
    public ResponseEntity<?> removeProductById(@PathVariable int id) throws Exception {
        imageService.deleteImagesBaseOnProductId(id);
        productService.removeProductById(id);
        return ResponseEntity.status(HttpStatus.OK.value()).body("Delete product with " + id + " successfully");
    }

    @PatchMapping("/api/v1/products/{id}")
    public ResponseEntity<?> updateProductById(@PathVariable int id, @RequestBody ProductRequestDTO productRequestDTO) throws Exception {
        ProductDetailResponseDTO productDetailResponseDTO = productService.updateProductById(id, productRequestDTO);
        return ResponseEntity.status(HttpStatus.OK.value()).body(productDetailResponseDTO);
    }

    @PatchMapping("/api/v1/products/state/{id}")
    public ResponseEntity<?> changeStateOfProductBaseOnId(@PathVariable int id, @RequestBody ProductStateUpdateRequestDTO productStateUpdateRequestDTO) throws Exception {
        ProductDetailResponseDTO productDetailResponseDTO = productService.changeStateOfProductBaseOnId(id, productStateUpdateRequestDTO);
        return ResponseEntity.status(HttpStatus.OK.value()).body(productDetailResponseDTO);
    }
}
