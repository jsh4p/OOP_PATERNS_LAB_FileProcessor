package org.jshap;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;

public abstract class FileProcessor {
    protected ArrayList<Path> paths;

    FileProcessor(ArrayList<Path> paths) throws IOException { // хэндлить ли этот троу?
        if (!isValidPaths(paths)) {
            throw new IOException("One or more paths are invalid\n");
        }

        this.paths = paths;
    }


    public final void output(int ind) {
        if (ind < 0 || ind > paths.size() - 1) {
            throw new IllegalArgumentException("ind must be between 0 and path.size() - 1\n");
        }

        try (FileReader fr = new FileReader(paths.get(ind).toFile()); BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected final void process() {
        execute();
        hook();
    }

    public static boolean isValidPaths(ArrayList<Path> paths) {
        if (paths == null) {
            throw new NullPointerException("paths is a null pointer\n");
        }

        if (paths.size() < 2) {
            throw new IllegalArgumentException("Inappropriate size of ArrayList paths\n");
        }

        for (Path path : paths) {
            if (!path.isAbsolute() || !Files.exists(path)) {
                return false;
            }
        }

        return true;
    }

    protected abstract void execute();

    protected void hook() {
        output(paths.size() - 1);
    }
}
