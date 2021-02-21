package com.dsalazar.reddit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotNull
    @Email
    private String email;

    @NotNull
    private String username;

    @NotNull
    @Size(min = 8, max = 20)
    private String password;

}
