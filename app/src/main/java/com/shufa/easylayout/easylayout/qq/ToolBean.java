package com.shufa.easylayout.easylayout.qq;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/5/8 0008.
 */

public class ToolBean implements Serializable {

    private int imgId;
    private String name;

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
