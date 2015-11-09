package com.exsoft.nandaamelyar.absensikaryawan.domain;

/**
 * Created by kresek on 09/11/15.
 */
public class Karyawan {
    private String id;
    private String nama;
    private String jabatan;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String jabatan) {
        this.jabatan = jabatan;
    }
}
