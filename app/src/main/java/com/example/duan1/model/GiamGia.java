package com.example.duan1.model;

import java.util.Date;

public class GiamGia {
    public String code;

    public Integer PhanTramGiam;



    public GiamGia(){

    }

    public GiamGia(String code, Integer phanTramGiam) {
        this.code = code;
        PhanTramGiam = phanTramGiam;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPhanTramGiam() {
        return PhanTramGiam;
    }

    public void setPhanTramGiam(Integer phanTramGiam) {
        PhanTramGiam = phanTramGiam;
    }
}
