package com.example.featuretogglelibrary;

import com.example.featuretogglelibrary.api.FeatureController;

public class FeatureToggle
{
    private  static FeatureController featureController=new FeatureController();
    public interface Callback_Data<T> {
        void data(T value);
    }








}
