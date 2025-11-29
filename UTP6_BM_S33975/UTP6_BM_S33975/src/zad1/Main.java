/**
 *
 *  @author Berlak Michał S33975
 *
 */

package zad1;

import java.util.*;
import java.util.stream.*;
import java.io.*;
import java.net.*;


public class Main {
  public static void main(String[] args) {
      try{
          // :3
          //URL url = new URL("http://wiki.puzzlers.org/pub/wordlists/unixdict.txt");
          URL url = new URL("https://raw.githubusercontent.com/pmichaud/rpbench/master/files/unixdict.txt");
          BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));

          Map<String, List<String>> anagramy = reader.lines().collect(Collectors.groupingBy(slowo -> {
              char[] znaki = slowo.toCharArray();
              Arrays.sort(znaki);
              return new String(znaki);
          }));

          int maxLiczba = anagramy.values().stream().mapToInt(lista -> lista.size()).max().orElse(0);

          anagramy.values().stream()
                  .filter(lista -> lista.size() == maxLiczba)
                  .forEach(lista -> {
                      String wynik = "";
                      for(String s : lista){
                          wynik = wynik + s + " ";
                      }
                      System.out.println(wynik.trim());
                  });
      }catch (Exception e){
          e.printStackTrace();
      }
  }
}


