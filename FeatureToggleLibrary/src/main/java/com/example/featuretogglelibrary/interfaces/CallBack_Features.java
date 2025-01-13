package com.example.featuretogglelibrary.interfaces;

import com.example.featuretogglelibrary.model.FeatureToggleItem;

import java.util.List;

public interface CallBack_Features {
    void ready(List<FeatureToggleItem> features);

    void failed(String message);

}
