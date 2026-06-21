

package com.mycompany.filexplorer.utils;
import java.util.Arrays;

/**
 *
 * @author jwd
 * @author Esteban Torres Jiménez
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