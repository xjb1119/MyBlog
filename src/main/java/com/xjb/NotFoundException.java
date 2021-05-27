package com.xjb;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 自定义找不到时的异常
 * @author Bo
 * @create 2021-04-15 18:40
 */
@ResponseStatus(HttpStatus.NOT_FOUND)//表示为找不到状态码
public class NotFoundException extends RuntimeException{
    public NotFoundException() {
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
