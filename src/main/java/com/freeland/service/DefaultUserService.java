package com.freeland.service;

import com.freeland.dao.po.User;
import com.freeland.dao.repositoy.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *
 * @author heiqie
 * @date 2018/7/11
 */
@Slf4j
@Service
public class DefaultUserService implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByName(String name) {
        return userRepository.findByUsername(name);
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

    @Override
    public boolean exist(String name) {
        User user = userRepository.findByUsername(name);
        return user != null;
    }

    @Override
    public boolean register(User user) {
        if (!exist(user.getUsername())) {
            insert(user);
            return true;
        }
        log.error("username has been registered:{}", user.getUsername());
        return false;
    }

}
