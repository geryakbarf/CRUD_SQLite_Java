package com.ergnologi.uts_gery.utils;

import com.ergnologi.uts_gery.models.MahasiswaModels;

import java.util.ArrayList;

public interface LoadDataCallback {
    void preExecute();
    void postExecute(ArrayList<MahasiswaModels> data);
}
