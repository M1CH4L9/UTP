/**
 *
 *  @author Berlak Michał S33975
 *
 */

package zad1;

import java.util.ArrayList;
import java.util.List;

public class ListCreator<T> { // Uwaga: klasa musi być sparametrtyzowana
    private final List<T> source;
    private Selector<? super T> selector;

    private ListCreator(List<T> source){
        this.source = source;
    }

    public static<T> ListCreator<T> collectFrom(List<T> source){
        return new ListCreator<>(source);
    }

    public ListCreator<T> when(Selector<? super T> selector){
        this.selector = selector;
        return this;
    }

    public <R> List<R> mapEvery(Mapper<? super T, R> mapper){
        if (mapper == null){
            throw new IllegalArgumentException("cant be null");
        }

        List<R> result = new ArrayList<>();

        if (source == null){
            return result;
        }
        for (T elem : source) {
            if((selector == null) || (selector.select(elem) == true)){
                R mapped = mapper.map(elem);
                result.add(mapped);
            }
        }
        return result;
    }
}  
