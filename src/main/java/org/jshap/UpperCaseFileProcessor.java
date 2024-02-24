package org.jshap;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.io.IOException;

final public class UpperCaseFileProcessor extends FileProcessor {
    UpperCaseFileProcessor(ArrayList<Path> paths) throws IOException {
        super(paths);
    }

    @Override
    protected void execute() {
        try (FileWriter fw = new FileWriter(paths.getLast().toFile())) {
            for (int i = 0; i < paths.size() - 1; ++i) {
                try (FileReader fr = new FileReader(paths.get(i).toFile());
                     BufferedReader br = new BufferedReader(fr)) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        fw.write(line.toUpperCase() + '\n');
                        fw.flush();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
