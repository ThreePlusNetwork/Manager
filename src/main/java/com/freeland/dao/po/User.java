package com.freeland.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author heiqie
 * @date 2018/7/11
 */
@Data
@Table(name = "user")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private String username;

    private String password;

    private String email;

    private int age;

    private Date createdTime;

    private Date updatedTime;

    private String createdBy;

    private String updatedBy;


}
