/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package utils;
import java.util.Arrays;

/**
 *
 * @author jwd
 */
public class Command {
    private final String name;
    private final String[] args;

    public Command(String input) {
        String[] parts = input.trim().split("\\s+");
        this.name = parts[0];
        this.args = Arrays.copyOfRange(parts, 1, parts.length);
    }

    public String getName() {
        return name;
    }

    public String[] getArgs() {
        return args;
    }
    
    public String arg(int index) {
        return index < args.length ? args[index] : null;
    }
}