package com.dinhchieu.demo.service.Impl;
import com.dinhchieu.demo.dao.ProductRepository;
import com.dinhchieu.demo.dao.StateRepository;
import com.dinhchieu.demo.dao.TypeRepository;
import com.dinhchieu.demo.dao.UserRepository;
import com.dinhchieu.demo.dto.request.ProductRequestDTO;
import com.dinhchieu.demo.dto.request.ProductStateUpdateRequestDTO;
import com.dinhchieu.demo.dto.response.PaginationResponseDTO;
import com.dinhchieu.demo.dto.response.ProductDetailResponseDTO;
import com.dinhchieu.demo.entity.*;
import com.dinhchieu.demo.handle.ProductNotFoundException;
import com.dinhchieu.demo.handle.StateNotFoundException;
import com.dinhchieu.demo.handle.TypeNotFoundException;
import com.dinhchieu.demo.handle.UserNotFoundException;
import com.dinhchieu.demo.service.ProductService;
import com.dinhchieu.demo.utils.Constants;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private TypeRepository typeRepository;

    private ProductDetailResponseDTO mapToProductDetailResponseDTO(Product product){
        ProductDetailResponseDTO dto = new ProductDetailResponseDTO();

        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setQuantity(product.getQuantity());
        dto.setWarranty(product.getWarranty());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setCreateAt(product.getCreateAt());

        dto.setTypeName(product.getType().getTypeName());
        dto.setOwnerUsername(product.getOwner().getUsername());

        dto.setState(product.getState().getName());

        if(product.getImageList() != null){
            List<String> imageDataList = product.getImageList().stream()
                    .map(x -> Constants.BASE_URL + "/api/v1/images/" + x.getId())
                    .toList();

            dto.setImageDataList(imageDataList);
        }

        return dto;
    }

    @Override
    public PaginationResponseDTO<ProductDetailResponseDTO> showAllProducts(int pageNo, int pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Product> pageResult = productRepository.findAll(paging);

        List<ProductDetailResponseDTO> productDetailResponseDTOList = pageResult.stream().map(this::mapToProductDetailResponseDTO).toList();

        PaginationResponseDTO<ProductDetailResponseDTO> response = new PaginationResponseDTO<>();
        response.setItems(productDetailResponseDTOList);
        response.setCurrentPage(pageResult.getNumber());
        response.setTotalItems(pageResult.getTotalElements());
        response.setTotalPages(pageResult.getTotalPages());
        response.setPageSize(pageResult.getSize());

        return response;
    }

    @Override
    public PaginationResponseDTO<ProductDetailResponseDTO> showAllProductsByUserId(int userId, int pageNo, int pageSize, String sortBy) {

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with ID " + userId + " not found!"));

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<Product> pageResult = productRepository.findAllByOwnerId(userId, paging);

        List<ProductDetailResponseDTO> productDetailResponseDTOList = pageResult.stream().map(this::mapToProductDetailResponseDTO).toList();

        PaginationResponseDTO<ProductDetailResponseDTO> response = new PaginationResponseDTO<>();
        response.setItems(productDetailResponseDTOList);
        response.setCurrentPage(pageResult.getNumber());
        response.setTotalItems(pageResult.getTotalElements());
        response.setTotalPages(pageResult.getTotalPages());
        response.setPageSize(pageResult.getSize());

        return response;
    }

    @Override
    public Optional<ProductDetailResponseDTO> getProductById(int id) throws Exception {
        if(!productRepository.existsById(id)){
            throw new ProductNotFoundException("Can't find product with id " + id);
        }

        Optional<Product> existingProduct = productRepository.findById(id);

        return existingProduct.map(this::mapToProductDetailResponseDTO);
    }

    @Override
    @Transactional
    public ProductDetailResponseDTO addProduct(ProductRequestDTO productRequestDTO) {
        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setQuantity(productRequestDTO.getQuantity());
        product.setDescription(productRequestDTO.getDescription());
        product.setPrice(productRequestDTO.getPrice());
        product.setWarranty(productRequestDTO.getWarranty());
        product.setCreateAt(System.currentTimeMillis());

        User existingUser = userRepository.findById(productRequestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User with ID " + productRequestDTO.getUserId() + " not found!"));
        product.setOwner(existingUser);

        Type existingType = typeRepository.findById(productRequestDTO.getTypeId())
                .orElseThrow(() -> new TypeNotFoundException("Type with ID " + productRequestDTO.getTypeId() + " not found!"));
        product.setType(existingType);

        State pendingState = stateRepository.findByName("PENDING");
        if (pendingState == null) {
            throw new StateNotFoundException("State 'PENDING' not found!");
        }
        product.setState(pendingState);

        Product savedProduct = productRepository.save(product);

        return mapToProductDetailResponseDTO(savedProduct);
    }


    @Override
    @Transactional
    public void removeProductById(int id)  throws Exception{
        if(!productRepository.existsById(id)){
            throw new ProductNotFoundException("Can't find product with id " + id);
        }
        else {
            productRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public ProductDetailResponseDTO updateProductById(int id, ProductRequestDTO productRequestDTO) throws Exception {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Can't find product with id " + id));

        Type existingType = typeRepository.findById(productRequestDTO.getTypeId())
                        .orElseThrow(() -> new TypeNotFoundException("Can't find type with id " + productRequestDTO.getTypeId()));

        existingProduct.setName(productRequestDTO.getName());
        existingProduct.setQuantity(productRequestDTO.getQuantity());
        existingProduct.setDescription(productRequestDTO.getDescription());
        existingProduct.setPrice(productRequestDTO.getPrice());
        existingProduct.setWarranty(productRequestDTO.getWarranty());
        existingProduct.setType(existingType);

        Product product = productRepository.save(existingProduct);
        return mapToProductDetailResponseDTO(product);
    }

    @Override
    @Transactional
    public ProductDetailResponseDTO changeStateOfProductBaseOnId(int id, ProductStateUpdateRequestDTO productStateUpdateRequestDTO) throws Exception {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Can't find product with id " + id));

        State existingState = stateRepository.findById(productStateUpdateRequestDTO.getStateId())
                .orElseThrow(() -> new StateNotFoundException("Can't find state with id " + productStateUpdateRequestDTO.getStateId()));

        if ("REJECTED".equalsIgnoreCase(existingState.getName())) {
            if (productStateUpdateRequestDTO.getRejectionReason() == null || productStateUpdateRequestDTO.getRejectionReason().isEmpty()) {
                throw new Exception("Rejection reason is required for REJECTED state.");
            }
            existingProduct.setRejectionReason(productStateUpdateRequestDTO.getRejectionReason());
        } else {
            existingProduct.setRejectionReason(null);
        }

        existingProduct.setState(existingState);

        Product product= productRepository.save(existingProduct);

        return mapToProductDetailResponseDTO(product);
    }
}
