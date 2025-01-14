package com.example.featuretogglelibrary.interfaces;

public interface GenericCallBack <T> {
    void success(T data); // For success responses
    void error(String error);     // For failure responses
}
