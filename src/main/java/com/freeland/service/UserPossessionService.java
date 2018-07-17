package com.freeland.service;

import com.freeland.dao.po.UserPossession;

/**
 *
 * @author heiqie
 * @date 2018/7/11
 */
public interface UserPossessionService {

    UserPossession findByUserId(Long userId);

    UserPossession save(UserPossession userPossession);

}
