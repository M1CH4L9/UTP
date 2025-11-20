/**
 *
 *  @author Berlak Michał S33975
 *
 */

package zad2;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class Anagrams {
    private final Map<String, List<String>> bySignature = new HashMap<>();
    private final Set<String> dictionary = new HashSet<>();

    public Anagrams(String allWordsPath) throws FileNotFoundException {
        loadAllWords(allWordsPath);
    }

    private static String signature(String s){
        char[] a = s.toCharArray();
        Arrays.sort(a);
        return new String(a);
    }

    private void loadAllWords(String path){
        try (Scanner sc = new Scanner(new File(path))){
            while (sc.hasNext()){
                String w = sc.next().trim();
                if(w.isEmpty()){
                    continue;
                }
                dictionary.add(w);
                bySignature.computeIfAbsent(signature(w), k -> new ArrayList<>()).add(w);
            }
        } catch(FileNotFoundException e){
            throw new RuntimeException("Brak pliku allwords.txt w {user.home}", e);
        }
        bySignature.values().forEach(list -> list.sort(Comparator.naturalOrder()));
    }

    public List<List<String>> getSortedByAnQty(){
        List<List<String>> groups = new ArrayList<>(bySignature.values());
        groups.sort(Comparator
                .<List<String>>comparingInt(List::size).reversed()
                .thenComparing(list -> list.get(0)));
        return groups;
    }

    public String getAnagramsFor(String word){
        if(!dictionary.contains(word)){
          return word + ": null";
        }
        List<String> group = bySignature.getOrDefault(signature(word), Collections.emptyList());

        List<String> anas = group.stream()
                .filter(w -> !w.equals(word))
                .collect(Collectors.toList());
        return word + ": " + anas.toString();
    }
}  
