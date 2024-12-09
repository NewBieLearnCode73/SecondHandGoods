package com.dinhchieu.demo.service;

import com.dinhchieu.demo.dto.request.ImageUpdateDTO;
import com.dinhchieu.demo.dto.request.ImageUploadRequestDTO;
import com.dinhchieu.demo.dto.response.ImageResponseDTO;

import java.util.List;

public interface ImageService {
    public String uploadImage(ImageUploadRequestDTO imageUploadRequestDTO) throws Exception;
    public void deleteImage(int imageId);
    public ImageResponseDTO getImageBaseOnId(int imageId);
    public String updateImage(int imageId, ImageUpdateDTO imageUpdateDTO) throws Exception;
}
