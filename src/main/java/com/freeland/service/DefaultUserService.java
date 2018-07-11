package com.freeland.service;

import com.freeland.dao.po.User;
import com.freeland.dao.repositoy.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author heiqie
 * @date 2018/7/11
 */
@Service
@Slf4j
public class DefaultUserService implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public User insert(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        Optional<User> option = userRepository.findById(id);
        if (!option.isPresent()) {
            log.error("can not found user by userId:{}", id);
            return null;
        }
        return option.get();
    }
}
