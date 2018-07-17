package com.freeland.service;

import com.freeland.constant.InteractionType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 *
 * @author heiqie
 * @date 2018/7/17
 */
@Slf4j
@Service
public class InteractionStrategy implements ApplicationContextAware {

    private ApplicationContext applicationContext;


    public Interaction getByType(InteractionType type) {

        log.info("can not found bean by handle interaction type :{}", type);
        throw new IllegalArgumentException("unknown handle interaction type :" + type);
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
