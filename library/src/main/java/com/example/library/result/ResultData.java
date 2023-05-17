package com.example.library.result;

import lombok.Data;

@Data
public class ResultData<T> {
    private int code;
    private String msg;
    private T data;
    public static <T> ResultData<T> success(T data) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setCode(ReturnCode.RC200.getCode());
        resultData.setMsg(ReturnCode.RC200.getMsg());
        resultData.setData(data);
        return resultData;
    }
    public static <T> ResultData<T> fail(int code, String msg) {
        ResultData<T> resultData = new ResultData<>();
        resultData.setCode(code);
        resultData.setMsg(msg);
        return resultData;
    }
}

