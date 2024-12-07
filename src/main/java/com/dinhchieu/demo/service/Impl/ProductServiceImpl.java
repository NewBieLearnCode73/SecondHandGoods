package com.dinhchieu.demo.service.Impl;
import com.dinhchieu.demo.dao.ProductRepository;
import com.dinhchieu.demo.dao.StateRepository;
import com.dinhchieu.demo.dao.TypeRepository;
import com.dinhchieu.demo.dao.UserRepository;
import com.dinhchieu.demo.dto.request.ProductRequestDTO;
import com.dinhchieu.demo.dto.request.ProductStateUpdateRequestDTO;
import com.dinhchieu.demo.entity.Product;
import com.dinhchieu.demo.entity.State;
import com.dinhchieu.demo.entity.Type;
import com.dinhchieu.demo.entity.User;
import com.dinhchieu.demo.service.ProductService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<Product> showAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> getProductById(int id) throws Exception {
        if(!productRepository.existsById(id)){
            throw new Exception("Can't find product with id " + id);
        }

        return productRepository.findById(id);
    }

    @Override
    @Transactional
    public Product addProduct(ProductRequestDTO productRequestDTO) {
        Product product = modelMapper.map(productRequestDTO, Product.class);

        User existingUser = userRepository.findById(productRequestDTO.getUserId()).orElseThrow( () -> new RuntimeException("User not found!"));
        Type existingType = typeRepository.findById(productRequestDTO.getTypeId()).orElseThrow( () -> new RuntimeException("Type with id " + productRequestDTO.getTypeId() + " not found!"));

        product.setOwner(existingUser);
        product.setType(existingType);
        product.setCreateAt(System.currentTimeMillis());

        Optional<State> pendingState = Optional.ofNullable(stateRepository.findByName("PENDING"));

        if(pendingState.isEmpty()){
            throw new RuntimeException("State 'PENDING' not found!");
        }
        else{
            product.setState(pendingState.get());
        }

        return productRepository.save(product);
    }

    @Override
    @Transactional
    public void removeProductById(int id)  throws Exception{
        if(!productRepository.existsById(id)){
            throw new Exception("Can't find product with id " + id);
        }
        else {
            productRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public Product updateProductById(int id, ProductRequestDTO productRequestDTO) throws Exception {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new Exception("Can't find product with id " + id));

        Type existingType = typeRepository.findById(productRequestDTO.getTypeId())
                        .orElseThrow(() -> new Exception("Can't find type with id " + productRequestDTO.getTypeId()));

        existingProduct.setName(productRequestDTO.getName());
        existingProduct.setQuantity(productRequestDTO.getQuantity());
        existingProduct.setDescription(productRequestDTO.getDescription());
        existingProduct.setWarranty(productRequestDTO.getWarranty());
        existingProduct.setType(existingType);

        return productRepository.save(existingProduct);
    }

    @Override
    @Transactional
    public Product changeStateOfProductBaseOnId(int id, ProductStateUpdateRequestDTO productStateUpdateRequestDTO) throws Exception {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new Exception("Can't find product with id " + id));

        State existingState = stateRepository.findById(productStateUpdateRequestDTO.getStateId())
                .orElseThrow(() -> new Exception("Can't find state with id " + productStateUpdateRequestDTO.getStateId()));

        if ("REJECTED".equalsIgnoreCase(existingState.getName())) {
            if (productStateUpdateRequestDTO.getRejectionReason() == null || productStateUpdateRequestDTO.getRejectionReason().isEmpty()) {
                throw new Exception("Rejection reason is required for REJECTED state.");
            }
            existingProduct.setRejectionReason(productStateUpdateRequestDTO.getRejectionReason());
        } else {
            existingProduct.setRejectionReason(null);
        }

        existingProduct.setState(existingState);

        return productRepository.save(existingProduct);
    }
}
