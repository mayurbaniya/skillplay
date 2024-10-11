package com.skillplay.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRequestDto {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;

}
