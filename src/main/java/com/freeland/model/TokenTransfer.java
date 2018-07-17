package com.freeland.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by heiqie on 2018/7/17.
 */
@Data
public class TokenTransfer {
    private Long userId;
    private BigDecimal amount;
    private String toAddress;
}
