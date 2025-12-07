/**
 *
 *  @author Berlak Michał S33975
 *
 */

package zad2;


import java.io.*;
import java.util.concurrent.*;

public class Main {

  public static void main(String[] args) {
      BlockingQueue<Towar> kolejka = new ArrayBlockingQueue<>(100);
      Towar POISON_PILL = new Towar(-1, -1);

      Thread watekA = new Thread(() -> {
          try(BufferedReader br = new BufferedReader(new FileReader("../Towary.txt"))) {
              String line;
              int count = 0;
              while ((line = br.readLine()) != null){
                  String[] parts = line.split("\\s+");
                  if(parts.length >= 2){
                      int id = Integer.parseInt(parts[0]);
                      double waga = Double.parseDouble(parts[1]);

                      Towar t = new Towar(id, waga);
                      kolejka.put(t);

                      count++;
                      if(count % 200 == 0){
                          System.out.println("utworzono " + count + " obiektów");
                      }
                  }
              }
              kolejka.put(POISON_PILL);
          } catch (IOException | InterruptedException e){
              e.printStackTrace();
          }
      });
      Thread watekB = new Thread(() -> {
          double sumaWag = 0;
          int count = 0;
          try{
              while(true){
                  Towar t = kolejka.take();
                  if(t == POISON_PILL){
                      break;
                  }
                  sumaWag += t.getWaga();
                  count++;

                  if(count % 100 == 0){
                      System.out.println("policzono wage " + count + " towarów");
                  }
              }
              System.out.println("Sumaryczna waga: " + sumaWag);
          }catch (InterruptedException e){
              e.printStackTrace();
          }
      });

      watekA.start();
      watekB.start();
  }
}
