package com.freeland.service;

import com.freeland.dao.po.UserPossession;
import com.freeland.dao.repositoy.UserPossessionRepository;
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
}
