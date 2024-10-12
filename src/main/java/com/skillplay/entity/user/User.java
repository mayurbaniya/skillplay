package com.skillplay.entity.user;

import com.skillplay.utils.AppConstants;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String username;
    private String email;

    private String status = AppConstants.ACTIVE.get();   // 1 active, 0 deleted, 99 banned // 2 admin

    private String password;
    @Temporal(TemporalType.DATE)
    private Date created = new Date();

    @Temporal(TemporalType.DATE)
    private Date modified = new Date();


}


