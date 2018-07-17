package com.freeland.service;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by heiqie on 2018/7/17.
 */
public abstract class AbstractInteraction {

    @Autowired
    protected UserService userService;

    @Autowired
    protected UserPossessionService userPossessionService;

}
