package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data // to string
@Getter //
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    @NotBlank(message = "Product's name cannot empty")
    @Size( min =3, max = 200, message = "title must be between 3 and 200 characters")
    private String name;

    @Min(value =0, message = "Price must be greater than or equal to 0")
    @Max(value= 199900000, message = "Price must be less than or equal to 199900000")
    private int price;

    @Min(value= 0,message = "Discount must be greater than or equal to 0")
    @Max(value=100, message = "Discount must be less than or equal to 100")
    private int discount;
    private String description;
    private String thumbnail;

    @JsonProperty("category_id")
    private String categoryID;

    private List<MultipartFile> files;
}
