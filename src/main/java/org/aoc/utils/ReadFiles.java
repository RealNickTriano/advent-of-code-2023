package org.aoc.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Collectors;

public class ReadFiles {

    public static String readFileToString(String path) throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }
}
