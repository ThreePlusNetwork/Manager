package com.freeland.service.interaction;

import com.freeland.constant.InteractionType;

/**
 *
 * @author heiqie
 * @date 2018/7/17
 * 处理交互
 */
public interface Interaction {



    Long handleInteraction(Long userId, InteractionType interactionType);
}
