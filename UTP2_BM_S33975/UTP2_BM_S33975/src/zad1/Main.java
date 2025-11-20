/**
 *
 *  @author Berlak Michał S33975
 *
 */

package zad1;


import java.util.*;

public class Main {

    static List<String> getPricesInPLN(List<String> destinations, double xrate) {
        return ListCreator.collectFrom(destinations)
                .when(  s -> {
                            String[] p = s.split(" ");
                            return p.length >= 3 && "WAW".equals(p[0]);
                        }
                )
                .mapEvery( s-> {
                            String[] p = s.split(" ");
                            int eur = Integer.parseInt(p[2]);
                            int pln = (int)(eur * xrate);
                            return "to " + p[1] + " - price in PLN: " + pln;
                        }
                );
    }

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
        List<String> result = getPricesInPLN(dest, ratePLNvsEUR);
        for (String r : result) System.out.println(r);
    }
}
