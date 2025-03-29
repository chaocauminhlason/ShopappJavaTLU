package com.example.shopapp.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data // to string
@Getter //
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDTO {

    @JsonProperty("phone_number")
    @NotBlank(message = "Phone number cant blank")
    private String phoneNumber;

    @NotBlank( message = "password cant be blank")
    private String password;

}
