package com.hackathon.wash_p.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hackathon.wash_p.data.request.Apply_wash;
import com.hackathon.wash_p.data.response.List_wash;

public class Viewmodel_fragment extends ViewModel {

    private MutableLiveData<List_wash> live_Data = new MutableLiveData<>();

    public LiveData<List_wash> getWash(){
        return live_Data;
    }

    public void setWash(List_wash wash){
        live_Data.setValue(wash);
    }

}