package com.freeland.model;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by heiqie on 2018/7/17.
 */
@Data
public class Interaction {

    @NotNull
    private Long userId;

    @NotNull
    private Integer interactionType;
}
