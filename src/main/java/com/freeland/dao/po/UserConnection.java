package com.freeland.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by heiqie on 2018/7/17.
 * 用户连线表
 */
@Data
@Table(name = "user_connection")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private Long sendUserId;

    private Long receiveUserId;

    private Long interactionType;



    private Date createdTime;

    private Date updatedTime;

    private String createdBy;

    private String updatedBy;
}
