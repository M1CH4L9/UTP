package zad2;

import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Futil {
    public static void processDir(String dirName, String resultFileName){
        Path startDir = Paths.get(dirName);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(resultFileName), StandardCharsets.UTF_8))){
            Files.walkFileTree(startDir, new SimpleFileVisitor<Path>(){
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    if(file.getFileName().toString().endsWith(".txt")){
                        Charset inputCharset = Charset.forName("Cp1250");
                        try (BufferedReader reader = Files.newBufferedReader(file, inputCharset)){
                            String line;
                            while ((line = reader.readLine()) != null){
                                writer.write(line);
                                writer.newLine();
                            }
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
