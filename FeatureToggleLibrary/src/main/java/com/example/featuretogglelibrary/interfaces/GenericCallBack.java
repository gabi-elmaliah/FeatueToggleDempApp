package com.example.featuretogglelibrary.interfaces;

public interface GenericCallBack {
    void success(String message); // For success responses
    void error(String error);     // For failure responses
}
