package com.dinhchieu.demo.service.Impl;

import com.dinhchieu.demo.dao.ImageRepository;
import com.dinhchieu.demo.dao.ProductRepository;
import com.dinhchieu.demo.dto.request.ImageUpdateDTO;
import com.dinhchieu.demo.dto.request.ImageUploadRequestDTO;
import com.dinhchieu.demo.dto.response.ImageResponseDTO;
import com.dinhchieu.demo.entity.Image;
import com.dinhchieu.demo.entity.Product;
import com.dinhchieu.demo.handle.ImageNotFoundException;
import com.dinhchieu.demo.handle.ProductNotFoundException;
import com.dinhchieu.demo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public String uploadImage(ImageUploadRequestDTO imageUploadRequestDTO) throws Exception {
        Product existingProduct = productRepository.findById(imageUploadRequestDTO.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Can't find product with id " + imageUploadRequestDTO.getProductId()));

        if(imageUploadRequestDTO.getData() == null || imageUploadRequestDTO.getData().isEmpty()){
            throw new ImageNotFoundException("Data for image can't be null or empty");
        }

        Image image = new Image();
        image.setData(imageUploadRequestDTO.getData());
        image.setProduct(existingProduct);

        imageRepository.save(image);

        return "Upload image for product with id"+ imageUploadRequestDTO.getProductId()  +" successfully";
    }

    @Override
    public void deleteImage(int imageId) {
        if(imageRepository.existsById(imageId)){
            throw new ImageNotFoundException("Can't find image with id " + imageId);
        }

        imageRepository.deleteById(imageId);
    }

    @Override
    public ImageResponseDTO getImageBaseOnId(int imageId) {
        Optional<Image> image = imageRepository.findById(imageId);

        if (image.isEmpty()) {
            throw new ImageNotFoundException("Can't find image with id " + imageId);
        }

        ImageResponseDTO imageResponseDTO = new ImageResponseDTO();
        imageResponseDTO.setId(image.get().getId());
        imageResponseDTO.setData(image.get().getData());
        imageResponseDTO.setProductId(image.get().getProduct().getId());
        return imageResponseDTO;
    }

    @Override
    public String updateImage(int imageId, ImageUpdateDTO imageUpdateDTO) throws Exception {
        Optional<Image> image = imageRepository.findById(imageId);

        if (image.isEmpty()) {
            throw new ImageNotFoundException("Can't find image with id " + imageId);
        }

        if(imageUpdateDTO.getData() == null || imageUpdateDTO.getData().isEmpty()){
            throw new ImageNotFoundException("Data for image can't be null or empty");
        }

        image.get().setData(imageUpdateDTO.getData());
        imageRepository.save(image.get());

        return "Update image with id " + imageId + " successfully";
    }


}
