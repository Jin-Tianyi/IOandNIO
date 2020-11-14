package com.jty.io;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        //使用try-with-resources语法糖结构
        try (FileInputStream fis = new FileInputStream(FileDescriptor.in);){
            System.out.println("输入：");
            int a=fis.read();//标准输入a
            System.out.println(a);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
