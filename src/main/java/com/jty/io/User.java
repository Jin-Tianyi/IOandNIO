package com.jty.io;

import java.io.Serializable;

class User implements Serializable {
    private String id;
    private String name;
    public User(String id,String name){
        this.id=id;
        this.name=name;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
