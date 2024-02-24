package org.jshap;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        ArrayList<Path> paths = new ArrayList<>();
        for (Integer i = 1; i <= 4; ++i) {
            String pathStr = "C:\\Users\\jshap\\Desktop\\file" + i + ".txt";
            System.out.println(pathStr);
            paths.add(Path.of(pathStr));
        }

        System.out.println("\n------------\n");

        FileProcessor upperCase = new UpperCaseFileProcessor(paths);
        upperCase.process();

        System.out.println("\n------------\n");

        FileProcessor duplicateFinder = new DuplicateFinderFileProcessor(paths);
        duplicateFinder.process();
    }
}