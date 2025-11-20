package zad3;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class ProgLang {
    private final LinkedHashMap<String, List<String>> langsMap = new LinkedHashMap<>();
    private final LinkedHashMap<String, List<String>> progsMap = new LinkedHashMap<>();

    public ProgLang(String filename) throws IOException {
        load(filename);
    }

    private void load(String filename) throws IOException{
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.trim().isEmpty()){
                    continue;
                }
                String[] parts = line.split("\\t");
                String lang = parts[0].trim();
                LinkedHashSet<String> progsSet = new LinkedHashSet<>();
                for (int i=1; i<parts.length; i++){
                    String p = parts[i].trim();
                    if(!p.isEmpty()){
                        progsSet.add(p);
                    }
                }
                List<String> progs = new ArrayList<>(progsSet);
                langsMap.put(lang, progs);

                for(String p : progs){
                    progsMap.computeIfAbsent(p, k -> new ArrayList<>()).add(lang);
                }
            }
        }
    }

    public Map<String, List<String>> getLangsMap(){
        return unmodifiableLinkedCopy(langsMap);
    }

    public Map<String, List<String>> getProgsMap(){
        return unmodifiableLinkedCopy(progsMap);
    }

    public Map<String, List<String>> getLangsMapSortedByNumOfProgs(){
        Comparator<Map.Entry<String, List<String>>> cmp = Comparator
                .<Map.Entry<String, List<String>>>comparingInt(e -> e.getValue().size())
                .reversed()
                .thenComparing(Map.Entry::getKey);
        return sorted(langsMap, cmp);
    }

    public Map<String, List<String>> getProgsMapSortedByNumOfLangs(){
        Comparator<Map.Entry<String, List<String>>> cmp = Comparator
                .<Map.Entry<String, List<String>>>comparingInt(e -> e.getValue().size())
                .reversed()
                .thenComparing(Map.Entry::getKey);
        return sorted(progsMap, cmp);
    }

    public Map<String, List<String>> getProgsMapForNumOfLangsGreaterThan(int n){
        return filtered(progsMap, e -> e.getValue().size() > n);
    }

    //poprawa na uniwersalna mapę
    public static <K, V> Map<K, V> sorted(Map<K, V> map,
                                                    Comparator<Map.Entry<K, V>> comparator){
        return map.entrySet().stream()
                .sorted(comparator)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

    //poprawa na uniwersalną mapę v2.0
    public static <K, V> Map<K, V> filtered(Map<K, V> map,
                                                      Predicate<Map.Entry<K, V>> predicate){
        return map.entrySet()
                .stream()
                .filter(predicate)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new));
    }

    private static <K, V> Map<K, V> unmodifiableLinkedCopy(LinkedHashMap<K, V> src){
        LinkedHashMap<K, V> copy = new LinkedHashMap<>();
        for(Map.Entry<K, V> e : src.entrySet()){
            V v = e.getValue();
            if(v instanceof List){
                @SuppressWarnings("unchecked")
                        List<Object> list = new ArrayList<>((List<Object>) v);
                copy.put(e.getKey(), (V) Collections.unmodifiableList(list));
            } else{
                copy.put(e.getKey(), e.getValue());
            }
        }
        return Collections.unmodifiableMap(copy);
    }
}
