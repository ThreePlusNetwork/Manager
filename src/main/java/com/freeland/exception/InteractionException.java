package com.freeland.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by heiqie on 2018/7/17.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InteractionException extends RuntimeException {

    private int errCode;

    private String errMsg;


}
