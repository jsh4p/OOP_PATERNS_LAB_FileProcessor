package org.jshap;

import java.util.ArrayList;
import java.util.HashSet;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.io.IOException;

final public class DuplicateFinderFileProcessor extends FileProcessor {
    DuplicateFinderFileProcessor(ArrayList<Path> paths) throws IOException {
        super(paths);
    }

    private ArrayList<String> getWords(int ind) {
        if (ind < 0 || ind > paths.size() - 1) {
            throw new IllegalArgumentException("ind must be between 0 and path.size() - 1\n");
        }

        try (FileReader fr = new FileReader(paths.get(ind).toFile());
             BufferedReader br = new BufferedReader(fr)) {
            ArrayList<String> words = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                String[] strings = line.toUpperCase().splitWithDelimiters(" +", 0);
                for (String string : strings) {
                    if (string.isEmpty()) {
                        continue;
                    }

                    if (string.charAt(0) == '(') {
                        string = string.substring(1);
                    }

                    if (!Character.isDigit(string.charAt(0)) && !Character.isAlphabetic(string.charAt(string.length() - 1))) {
                        string = string.substring(0, string.length() - 1);
                    }

                    if (!string.isEmpty()) {
                        words.add(string);
                    }
                }
            }

            return words;
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void execute() {
        HashSet<String> hs = new HashSet<>(getWords(0));
        //System.out.println(hs);
        try (FileWriter fw = new FileWriter(paths.getLast().toFile())) {
            for (int i = 1; i < paths.size() - 1; ++i) {
                /*HashSet<String> nhs = new HashSet<>(getWords(i));
                System.out.println(nhs);*/
                if (new HashSet<>(getWords(i)).containsAll(hs)) {
                    fw.write(paths.get(i).toString() + '\n');
                    fw.flush();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}