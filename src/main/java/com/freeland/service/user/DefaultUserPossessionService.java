package com.freeland.service.user;

import com.freeland.dao.po.UserPossession;
import com.freeland.dao.repositoy.UserPossessionRepository;
import com.freeland.service.user.UserPossessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by heiqie on 2018/7/11.
 */
@Slf4j
@Service
public class DefaultUserPossessionService implements UserPossessionService {

    @Autowired
    private UserPossessionRepository userPossessionRepository;

    @Override
    public UserPossession findByUserId(Long userId) {
        UserPossession userPossession = userPossessionRepository.findByUserId(userId);
        if (userPossession == null) {
            log.error("can not found userPossession by userId:{}", userId);
            return null;
        }
        return userPossession;
    }

    @Override
    public UserPossession save(UserPossession userPossession) {
        return userPossessionRepository.save(userPossession);
    }

    @Override
    public UserPossession requestBounty(Long userId) {
        UserPossession userPossession = userPossessionRepository.findByUserId(userId);
        if (userPossession == null) {
            userPossession = UserPossession.builder().userId(userId)
                    .gold(0L)
                    .token(0L)
                    .build();
        }
        Long beforeGoldBalance = userPossession.getGold();
        Long newGoldBalance = beforeGoldBalance + 100L;
        userPossession.setGold(newGoldBalance);
        UserPossession updatedPossession = save(userPossession);
        log.info("userId:{},before gold:{},now gold", userId, beforeGoldBalance, newGoldBalance);
        return updatedPossession;
    }
}
