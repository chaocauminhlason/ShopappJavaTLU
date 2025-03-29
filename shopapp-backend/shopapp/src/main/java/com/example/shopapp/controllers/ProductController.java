package com.example.shopapp.controllers;

import com.example.shopapp.dtos.ProductDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @GetMapping("")
    public ResponseEntity<String> getProducts(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        return ResponseEntity.ok("get product here");
    }
    @GetMapping("/{id}")
    public ResponseEntity<String> getProductByid(
            @PathVariable("id") String productId) {
        return ResponseEntity.ok("Product with id = "+productId);
    }
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProduct (
            @Valid @ModelAttribute ProductDTO productDTO,
            //@RequestPart("file") MultipartFile file,
            BindingResult result
    ){
        try{
            if (result.hasErrors()) {
                List<String> errorMessage = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessage);
            }
            List<MultipartFile> files = productDTO.getFiles();
            files = files == null ? new ArrayList<MultipartFile>() :  files ;
            for(MultipartFile file : files){
                    if(file.getSize()==0)
                        continue;
                    if(file.getSize() > 10 * 1024 * 1024) { //10MB
                        throw new ResponseStatusException(
                                HttpStatus.PAYLOAD_TOO_LARGE, "FILE IS TOO LAGRE");
                    }
                    String contentType = file.getContentType();
                    if(contentType == null || !contentType.startsWith("image/"))
                        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                                .body("File must be image");
                    //lưu file và cạp nhật thumnail trong Dto
                    String filename = storeFile(file); // thay thế này với code của ba đeer lưu file


            // lưu vào đối ượng product trong database ,=> làm sau
            //lưu vào bảng product_image
//                {
//                    "name":"ipad pro 2023",
//                        "price": 812.34,
//                        "thumnail": "",
//                        "description": "this is a test product",
//                        "category_id": 1
//                }
            }

            return ResponseEntity.ok("Product created successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        // thêm uuid trước tên file để đảm bảo tên file là duy nhất
        String uniqueFileName = UUID.randomUUID().toString()+"_"+filename;
        // đường dẫn vào thư mucj muốn luu file
        java.nio.file.Path uploadDir = Paths.get("uploads");
        // Kiểm tra và tạo thư mục nêu nó không tồn tại
        if(!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
        // ĐƯơng dẫn đầydđủ đến file

        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        // sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id) {
        return ResponseEntity.ok("update product with id = "+id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        return ResponseEntity.ok("delete Product with id = "+id);
    }
}
