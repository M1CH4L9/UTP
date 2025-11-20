/**
 *
 *  @author Berlak Michał S33975
 *
 */

package zad1;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class CustomersPurchaseSortFind {
    private final List<Purchase> purchases = new ArrayList<>();

    public void readFile(String fname){
        purchases.clear();
        try(BufferedReader br = Files.newBufferedReader(Paths.get(fname))){
            String line;
            while ((line = br.readLine()) != null){
                line = line.trim();
                if(!line.isEmpty()){
                    purchases.add(new Purchase(line));
                }
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void showSortedBy(String header){
        System.out.println(header);
        switch (header){
            case "Nazwiska":{
                List<Purchase> sorted = purchases.stream()
                        .sorted(Comparator
                                .comparing(Purchase::getName)
                                .thenComparing(Purchase::getId))
                        .collect(Collectors.toList());
                sorted.forEach(p -> System.out.println(p.toString()));
                break;
            }
            case "Koszty": {
                List<Purchase> sorted = purchases.stream()
                        .sorted(Comparator
                                .comparingDouble(Purchase::getCost).reversed()
                                .thenComparing(Purchase::getId))
                        .collect(Collectors.toList());
                sorted.forEach(p -> System.out.println(p.toStringWithCost()));
                System.out.println();
                break;
            }
            default:

        }
        System.out.println();
    }
    public void showPurchaseFor(String id){
        System.out.println("Klient " + id);
        purchases.stream()
                .filter(p -> p.getId().equals(id))
                .forEach(p -> System.out.println(p.toString()));
        System.out.println();
    }

}
