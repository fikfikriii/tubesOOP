package com.monstersaku;

import com.monstersaku.util.CSVReader;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BackupMain {
    private static final List<String> CSV_FILE_PATHS = Collections.unmodifiableList(Arrays.asList(
            "configs/monsterpool.csv",
            "configs/movepool.csv",
            "configs/element-type-effectivity-chart.csv"));

    public static void main(String[] args) {
        for (String fileName : CSV_FILE_PATHS) {
            try {
                System.out.printf("Filename: %s\n", fileName);
                CSVReader reader = new CSVReader(new File(BackupMain.class.getResource(fileName).toURI()), ";");
                reader.setSkipHeader(true);
                List<String[]> lines = reader.read();
                System.out.println("=========== CONTENT START ===========");
                for (String[] line : lines) {
                    for (String word : line) {
                        System.out.printf("%s ", word);
                    }
                    System.out.println();
                }
                System.out.println("=========== CONTENT END ===========");
                System.out.println();
            } catch (Exception e) {
                // do nothing
            }
        }
    }
}
