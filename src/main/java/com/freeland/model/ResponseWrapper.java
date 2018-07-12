package com.freeland.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author heiqie
 * @date 2018/7/12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseWrapper<T> {

    private static int SUCCESS = 1;
    private static int ERROR = 0;

    private String errorMsg;
    private T data;
    private int code;

    public static <T> ResponseWrapper<T> success(T data){
        return ResponseWrapper.<T>builder()
                .code(SUCCESS)
                .data(data)
                .build();
    }

    public static <T> ResponseWrapper<T> error(String errorMsg) {
        return ResponseWrapper.<T>builder()
                .code(ERROR)
                .errorMsg(errorMsg)
                .build();
    }



}
