package com.freeland.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by heiqie on 2018/7/26.
 */
@Data
public class WalletCreatation {

    private Long userId;

    @NotNull
    private String password;
}
