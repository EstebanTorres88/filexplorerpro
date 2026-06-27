
package com.mycompany.filexplorer.utils;

import java.util.Scanner;
import java.util.function.Function;

/**
 *
 * @author Dilan Rojas Vargas
 * @author Esteban Torres Jiménez
 */
public class Reader {
    private static final Scanner SCANNER = new Scanner(System.in);

  
    public static <T> T read(Function<String, T> parser) {
        return parser.apply(SCANNER.nextLine());
    }
}