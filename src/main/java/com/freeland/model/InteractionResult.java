package com.freeland.model;

import lombok.Data;

/**
 * Created by heiqie on 2018/7/17.
 */
@Data
public class InteractionResult {
    private Long currentUserId;
    private Long goldBalance;
    private Long connectedUserId;
    private Long distance;
}
