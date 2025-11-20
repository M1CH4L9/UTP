//pozdrawiam jak ktoś to czyta :3
/**
 *
 *  @author Berlak Michał S33975
 *
 */

package zad2;

import java.util.*;
/*<-- niezbędne importy */

public class Main {

    public static void main(String[] args) {
        // Lista destynacji: port_wylotu port_przylotu cena_EUR
        List<String> dest = Arrays.asList(
                "bleble bleble 2000",
                "WAW HAV 1200",
                "xxx yyy 789",
                "WAW DPS 2000",
                "WAW HKT 1000"
        );
        double ratePLNvsEUR = 4.30;
        List<String> result = dest.stream()
                .map(s -> s.split(" "))
                .filter(p -> p[0].equals("WAW"))
                .map(p -> {
                    int eur = Integer.parseInt(p[2]);
                    int pln = (int)(eur*ratePLNvsEUR);
                    return "to " + p[1] + " - price in PLN: " + pln;
                })
                .toList();

        for (String r : result) System.out.println(r);
    }
}
