package com.freeland.service.interaction;

import com.freeland.constant.Error;
import com.freeland.constant.InteractionType;
import com.freeland.dao.po.UserPossession;
import com.freeland.exception.InteractionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author heiqie
 * @date 2018/7/17
 */
@Slf4j
@Service
public class RoseInteraction extends AbstractInteraction implements Interaction {


    @Override
    public Long handleInteraction(Long userId, InteractionType interactionType) {
        UserPossession userPossession = userPossessionService.findByUserId(userId);
        if (userPossession == null || userPossession.getGold() == null) {
            log.warn("user have not gold possession:{}", userId);
            throw new InteractionException(Error.NOT_ENOUGH_GOLD.getCode(), Error.NOT_ENOUGH_GOLD.getMsg());
        }
        int goldBalance = userPossession.getGold().intValue();
        int burnedGold = interactionType.getBurnedGold();
        if (burnedGold > goldBalance) {
            throw new InteractionException(Error.NOT_ENOUGH_GOLD.getCode(), Error.NOT_ENOUGH_GOLD.getMsg());
        }
        int remainedBalance = goldBalance - burnedGold;
        userPossession.setGold((long) remainedBalance);
        UserPossession updatedPossession = userPossessionService.save(userPossession);
        log.info("update gold possession success,before goldBalance :{},after goldBalance:{},userId:{}", goldBalance, remainedBalance, userId);
        return updatedPossession.getGold();
    }
}
