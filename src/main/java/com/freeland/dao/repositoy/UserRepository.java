package com.freeland.dao.repositoy;

import com.freeland.dao.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 * @author heiqie
 * @date 2018/7/11
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);
}
