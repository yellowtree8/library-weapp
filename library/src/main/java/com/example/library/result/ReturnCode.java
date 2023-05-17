package com.example.library.result;

import lombok.Getter;

@Getter
public enum ReturnCode {
    RC200(200, "success"),
    RC400(400, "fail");
    private final int code;
    private final String msg;
    ReturnCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
