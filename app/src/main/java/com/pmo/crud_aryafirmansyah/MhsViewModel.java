package com.pmo.crud_aryafirmansyah;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MhsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MhsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Data Mahasiswa");
    }

    public LiveData<String> getText() {
        return mText;
    }
}