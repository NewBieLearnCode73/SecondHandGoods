package com.dinhchieu.demo.service;

import com.dinhchieu.demo.dto.request.ImageUpdateDTO;
import com.dinhchieu.demo.dto.request.ImageUploadRequestDTO;
import com.dinhchieu.demo.dto.response.ImageResponseDTO;

import java.util.List;

public interface ImageService {
    String uploadImage(ImageUploadRequestDTO imageUploadRequestDTO) throws Exception;
    void deleteImage(int imageId) throws Exception;
    ImageResponseDTO getImageBaseOnId(int imageId);
    String updateImage(int imageId, ImageUpdateDTO imageUpdateDTO) throws Exception;
    void deleteImagesBaseOnProductId(int productId) throws Exception;
}
