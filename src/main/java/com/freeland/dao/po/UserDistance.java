package com.freeland.dao.po;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by heiqie on 2018/7/17.
 */
public class UserDistance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private Long sendUserId;

    private Long receiveUserId;

    private Long distance;
}
