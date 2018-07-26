package com.freeland.service.interaction;

import com.freeland.service.user.UserPossessionService;
import com.freeland.service.user.UserService;
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
