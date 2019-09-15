package com.marvelapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.marvelapp.model.ModelBase;

import java.util.HashMap;
import java.util.Map;

/**
 * ViewModel to share data between fragments
 */
public class SharedViewModel extends ViewModel {
    private Map<Class, ModelBase> mapOfModelLiveData = new HashMap<>();
    private MutableLiveData<ModelBase> modelLiveData = new MutableLiveData<>();

    public MutableLiveData<ModelBase> getModel() {
        return modelLiveData;
    }

    public ModelBase getModel(Class classType) {
        return mapOfModelLiveData.get(classType);
    }

    public void setModel(Class classType, ModelBase modelBase) {
        mapOfModelLiveData.put(classType, modelBase);
    }
}
