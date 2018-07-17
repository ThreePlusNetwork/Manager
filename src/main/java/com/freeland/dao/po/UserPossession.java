package com.freeland.dao.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 *
 * @author heiqie
 * @date 2018/7/11
 */

@Data
@Table(name = "user_score")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPossession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private Long userId;

    private Long gold;

    private Long token;
    

}