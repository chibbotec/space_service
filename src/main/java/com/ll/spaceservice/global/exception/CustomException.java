package com.ll.spaceservice.global.exception;


import com.ll.spaceservice.global.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
}
