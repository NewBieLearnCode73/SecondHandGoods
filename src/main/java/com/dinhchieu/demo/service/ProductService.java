package com.dinhchieu.demo.service;
import com.dinhchieu.demo.dto.request.ProductRequestDTO;
import com.dinhchieu.demo.dto.request.ProductStateUpdateRequestDTO;
import com.dinhchieu.demo.dto.response.PaginationResponseDTO;
import com.dinhchieu.demo.dto.response.ProductDetailResponseDTO;

import java.util.Optional;

public interface ProductService {
    public PaginationResponseDTO<ProductDetailResponseDTO> showAllProducts(int pageNo, int pageSize, String sortBy);
    public PaginationResponseDTO<ProductDetailResponseDTO> showAllProductsByUserId(int userId, int pageNo, int pageSize, String sortBy);
    public Optional<ProductDetailResponseDTO> getProductById(int id) throws Exception;
    public ProductDetailResponseDTO addProduct(ProductRequestDTO productRequestDTO);
    public void removeProductById(int id) throws Exception;
    public ProductDetailResponseDTO updateProductById(int id , ProductRequestDTO productRequestDTO) throws Exception;
    public ProductDetailResponseDTO changeStateOfProductBaseOnId(int id, ProductStateUpdateRequestDTO productStateUpdateRequestDTO) throws Exception;
}