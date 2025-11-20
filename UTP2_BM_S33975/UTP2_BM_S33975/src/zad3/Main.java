//pozdrawiam jak ktoś to czyta :3
/**
 *
 *  @author Berlak Michał S33975
 *
 */

//cmd test
//java -cp out Main.java Warszawa 100 Kielce 200 Szczecin 300

package zad3;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.Function;
import java.util.regex.*;
/*<--
 *  niezbędne importy
 */
public class Main {
    public static void main(String[] args) {

        //flines
        Function<String, List<String>> flines = fname ->{
            try {
                return Files.readAllLines(Paths.get(fname));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        };

        //join
        Function<List<String>, String> join = lines -> String.join("", lines);

        //collectInts
        Function<String, List<Integer>> collectInts = text ->{
            List<Integer> numbers = new ArrayList<>();
            Matcher m = Pattern.compile("-?\\d+").matcher(text);
            while(m.find()){
                numbers.add(Integer.parseInt(m.group()));
            }
            return numbers;
        };

        //sum
        Function<List<Integer>, Integer> sum = numbers -> numbers.stream().mapToInt(i -> i).sum();

        /*<--
         *  definicja operacji w postaci lambda-wyrażeń:
         *  - collectInts - zwraca listę liczb całkowitych zawartych w napisie
         *  - sum - zwraca sumę elmentów listy liczb całkowitych
         */

        String fname = System.getProperty("user.home") + "/LamComFile.txt";
        InputConverter<String> fileConv = new InputConverter<>(fname);
        List<String> lines = fileConv.convertBy(flines);
        String text = fileConv.convertBy(flines, join);
        List<Integer> ints = fileConv.convertBy(flines, join, collectInts);
        Integer sumints = fileConv.convertBy(flines, join, collectInts, sum);

        System.out.println(lines);
        System.out.println(text);
        System.out.println(ints);
        System.out.println(sumints);

        List<String> arglist = Arrays.asList(args);
        InputConverter<List<String>> slistConv = new InputConverter<>(arglist);
        sumints = slistConv.convertBy(join, collectInts, sum);
        System.out.println(sumints);

    }
}