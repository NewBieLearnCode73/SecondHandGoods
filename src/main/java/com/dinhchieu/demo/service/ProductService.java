package com.dinhchieu.demo.service;
import com.dinhchieu.demo.dto.request.ProductRequestDTO;
import com.dinhchieu.demo.dto.request.ProductStateUpdateRequestDTO;
import com.dinhchieu.demo.entity.Product;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    public List<Product> showAllProducts();
    public Optional<Product> getProductById(int id) throws Exception;
    public Product addProduct(ProductRequestDTO productRequestDTO);
    public void removeProductById(int id) throws Exception;
    public Product updateProductById(int id , ProductRequestDTO productRequestDTO) throws Exception;
    public Product changeStateOfProductBaseOnId(int id, ProductStateUpdateRequestDTO productStateUpdateRequestDTO) throws Exception;
}