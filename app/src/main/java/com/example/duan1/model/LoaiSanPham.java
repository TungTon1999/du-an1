package com.example.duan1.model;

public class LoaiSanPham {
    public Integer id;
    public String tenLoai;

    public LoaiSanPham(){

    }

    public LoaiSanPham(Integer id, String tenLoai) {
        this.id = id;
        this.tenLoai = tenLoai;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}
