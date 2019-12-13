package com.pn.groupc.dailyfaces.ui.newsfeed;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NewsfeedViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NewsfeedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is news feed fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}