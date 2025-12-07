package zad2;
import java.io.*;
import java.util.*;

public class GeneratorTowarow {
    public static void main(String[] args) {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("../Towary.txt"))){
            Random rand = new Random();
            for (int i = 1; i <= 10005; i++) {
                int id = i;
                double waga = 1.0 + (100.0 - 1.0) * rand.nextDouble();
                bw.write(id + " " + String.format("%.2f", waga).replace(',', '.'));
                bw.newLine();
            }
            System.out.println("Plik wygenerowany.");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
