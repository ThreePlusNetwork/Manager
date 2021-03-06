package com.freeland.service;

import com.freeland.dao.po.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

/**
 *
 * @author heiqie
 * @date 2018/7/11
 */
public interface UserService extends UserDetailsService {


    User findByName(String name);

    User insert(User user);

    User findById(Long id);

    boolean exist(String name);

    boolean register(User user);



}
