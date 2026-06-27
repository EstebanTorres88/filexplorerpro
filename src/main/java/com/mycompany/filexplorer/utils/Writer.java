
package com.mycompany.filexplorer.utils;

/**
 *
 * @author Dilan Rojas Vargas
 * @author Esteban Torres Jiménez
 */
public class Writer<T> {
    public <T> void write(T msg) {
        System.out.print(msg);
    }
    
        public <T> void writeln(T msg) {
        System.out.println(msg);
    }
}