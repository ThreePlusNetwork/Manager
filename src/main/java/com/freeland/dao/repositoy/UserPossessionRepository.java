package com.freeland.dao.repositoy;

import com.freeland.dao.po.UserPossession;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author heiqie
 * @date 2018/7/11
 */
public interface UserPossessionRepository extends JpaRepository<UserPossession,Long> {
    UserPossession findByUserId(Long userId);
}
