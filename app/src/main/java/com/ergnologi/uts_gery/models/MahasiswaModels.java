package com.ergnologi.uts_gery.models;

import android.os.Parcel;
import android.os.Parcelable;

public class MahasiswaModels implements Parcelable {
    private String nim;
    private String nama;
    private String kelas;
    private String prodi;

    public String  getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.nim);
        parcel.writeString(this.nama);
        parcel.writeString(this.kelas);
        parcel.writeString(this.prodi);
    }

    public MahasiswaModels(){

    }

    protected MahasiswaModels(Parcel in) {
        nim = in.readString();
        nama = in.readString();
        kelas = in.readString();
        prodi = in.readString();
    }

    public static final Creator<MahasiswaModels> CREATOR = new Creator<MahasiswaModels>() {
        @Override
        public MahasiswaModels createFromParcel(Parcel in) {
            return new MahasiswaModels(in);
        }

        @Override
        public MahasiswaModels[] newArray(int size) {
            return new MahasiswaModels[size];
        }
    };
}
