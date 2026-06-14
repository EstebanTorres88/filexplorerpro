/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.filexplorer.utils;

/**
 *
 * @author jwd
 */
public class Writer<T> {
    public <T> void write(T msg) {
        System.out.print(msg);
    }
    
        public <T> void writeln(T msg) {
        System.out.println(msg);
    }
}