package com.dinhchieu.demo.controller;
import com.dinhchieu.demo.dto.request.ImageUpdateDTO;
import com.dinhchieu.demo.dto.request.ImageUploadRequestDTO;
import com.dinhchieu.demo.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping("/api/v1/images/{id}")
    public ResponseEntity<?> getImageBaseOnId(@PathVariable int id) {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.getImageBaseOnId(id));
    }

    @PostMapping("/api/v1/images")
    public ResponseEntity<?> uploadImage(@RequestBody ImageUploadRequestDTO imageUploadRequestDTO) throws Exception {
        String response = imageService.uploadImage(imageUploadRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/api/v1/images/{id}")
    public ResponseEntity<?> deleteImage(@PathVariable int id) throws Exception {
        imageService.deleteImage(id);
        return ResponseEntity.status(HttpStatus.OK).body("Delete image with id " + id + " successfully");
    }

    @PatchMapping("/api/v1/images/{id}")
    public ResponseEntity<?> updateImage(@PathVariable int id, @RequestBody ImageUpdateDTO imageUpdateDTO) throws Exception {
        String response = imageService.updateImage(id, imageUpdateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
