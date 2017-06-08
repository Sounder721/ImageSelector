package com.sounder.sounderselector.javabean;

/**
 *  Created by sounder on 2017/6/2.
 */

public class ThumbImage {
    private int id;
    private String data;

    public String toString(){
        return "id="+id+"\tdata="+data;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
