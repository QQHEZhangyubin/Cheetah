package com.example.desk.entity;

import java.io.Serializable;

public class User implements Serializable {


    /**
     * data : {"userid":"2016021022","college":"外国语学院","classs":"英语135","password":"25f9e794323b453885f5181f1b624d0b","birthday":"1998-10-23","email":"13608428279@163.com","gender":"男","userlogo":null}
     * error_code : 0
     */

    private DataBean data;
    private int error_code;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }


}
