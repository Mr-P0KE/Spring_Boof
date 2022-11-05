package com.poke.Comon;


/*
自定义异常，用于处理外键约束导致删除失败的问题
*/
public class CustomException extends  RuntimeException{
    public CustomException(String message) {
        super(message);
    }
}
