package zad2;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.stream.*;

public class Futil {

    public static void processDir(String dirName, String resultFileName) {

        Path resultFile = Paths.get(resultFileName);

        try {
            try (BufferedWriter writer = Files.newBufferedWriter(resultFile, StandardCharsets.UTF_8)) {
                Stream<Path> filesStream = Files.walk(Paths.get(dirName));

                filesStream
                        .filter(path -> Files.isRegularFile(path) && path.toString().endsWith(".txt"))
                        .forEach(path -> {
                            try {
                                Files.lines(path, Charset.forName("Cp1250")).forEach(linia -> {
                                    try {
                                        writer.write(linia);
                                        writer.newLine();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                });
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                filesStream.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
