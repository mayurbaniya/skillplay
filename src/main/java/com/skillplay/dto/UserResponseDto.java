package com.skillplay.dto;

import com.skillplay.entity.user.Tokens;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class UserResponseDto {

    private long id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String status;
    private Date created;
    private Date modified;
    private double tokens;
    private double miniTokens;

}
