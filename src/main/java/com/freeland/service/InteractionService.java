package com.freeland.service;

import com.freeland.constant.InteractionType;

/**
 * Created by heiqie on 2018/7/17.
 */
public interface InteractionService {

    Long handle(Long userId, InteractionType interactionType);
}
