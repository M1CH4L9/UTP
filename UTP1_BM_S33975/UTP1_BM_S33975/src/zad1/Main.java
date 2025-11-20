/**
 *
 *  @author Berlak Michał S33975
 *
 */

package zad1;



import java.util.*;

public class Main {
  public Main() {
    List<Integer> src1 = Arrays.asList(1, 7, 9, 11, 12);/*<-- tu dopisać inicjację elementów listy */
    System.out.println(test1(src1));

    List<String> src2 = Arrays.asList("a", "zzzz", "vvvvvvv");/*<-- tu dopisać inicjację elementów listy */
    System.out.println(test2(src2));
  }

  public List<Integer> test1(List<Integer> src) {
    Selector<Integer> sel = new Selector<Integer>(){/*<-- definicja selektora; bez lambda-wyrażeń; nazwa zmiennej sel */
      @Override
      public boolean select(Integer v){
        return v < 10;
      }
    };
    Mapper<Integer, Integer> map = new Mapper<Integer, Integer>(){    /*<-- definicja mappera; bez lambda-wyrażeń; nazwa zmiennej map */
      @Override
      public Integer map(Integer v){
        return v+10;
      }
    };
    return ListCreator.collectFrom(src).when(sel).mapEvery(map); /*<-- zwrot wyniku
      uzyskanego przez wywołanie statycznej metody klasy ListCreator:
     */
  }

  public List<Integer> test2(List<String> src) {
    Selector<String> sel = new Selector<String>(){ /*<-- definicja selektora; bez lambda-wyrażeń; nazwa zmiennej sel */
      @Override
      public boolean select(String s){
        return s.length() > 3;
      }
    };
    Mapper<String, Integer> map = new Mapper<String, Integer>(){   /*<-- definicja mappera; bez lambda-wyrażeń; nazwa zmiennej map */
      @Override
      public Integer map(String s){
        return s.length()+10;
      }
    };
    return ListCreator.collectFrom(src).when(sel).mapEvery(map);  /*<-- zwrot wyniku
      uzyskanego przez wywołanie statycznej metody klasy ListCreator:
     */
  }

  public static void main(String[] args) {
    new Main();
  }
}
