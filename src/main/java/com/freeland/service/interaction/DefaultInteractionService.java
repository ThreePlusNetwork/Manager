package com.freeland.service.interaction;

import com.freeland.constant.InteractionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author heiqie
 * @date 2018/7/17
 */
@Slf4j
@Service
public class DefaultInteractionService implements InteractionService{

    @Autowired
    private InteractionStrategy interactionStrategy;


    @Override
    public Long handle(Long userId, InteractionType interactionType) {
        Interaction interaction = interactionStrategy.getByType(interactionType);
        return interaction.handleInteraction(userId, interactionType);
    }
}
