package com.freeland.service;

import com.freeland.dao.po.User;

import java.util.List;

/**
 *
 * @author heiqie
 * @date 2018/7/11
 */
public interface UserService {

    List<User> findByName(String name);

    User insert(User user);

    User findById(Long id);
}
