package com.freeland.dao.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by heiqie on 2018/7/26.
 */
@Data
@Table(name = "user_wallet")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserWalllet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private Long userId;

    private String walletName;

    private boolean active;
}

