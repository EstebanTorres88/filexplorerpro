/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.filexplorer.utils;

import java.util.Scanner;
import java.util.function.Function;

/**
 *
 * @author jwd
 */
public class Reader {
    private static final Scanner SCANNER = new Scanner(System.in);

    // Ussage example:
    //    String name = Reader.read(s -> s);
    //    Integer age = Reader.read(Integer::parseInt);
    //    Double salary = Reader.read(Double::parseDouble);
    public static <T> T read(Function<String, T> parser) {
        return parser.apply(SCANNER.nextLine());
    }
}