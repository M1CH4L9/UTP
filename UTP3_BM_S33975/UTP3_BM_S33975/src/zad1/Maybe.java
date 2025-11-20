package zad1;
import java.util.*;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.NoSuchElementException;

public class Maybe<T> {
    private final T value;

    private Maybe(T value) {
        this.value = value;
    }
    private Maybe() {
        this.value = null;
    }

    public static <T> Maybe<T> of (T x){
        if( x != null){
            return new Maybe<>(x);
        }
        else{
            return new Maybe<>();
        }
    }

    public boolean isPresent(){
        return value != null;
    }

    @Override
    public String toString(){
        if (isPresent()){
            return "Maybe has value " + value;
        }
        else{
            return "Maybe is empty";
        }
    }

    public void ifPresent(java.util.function.Consumer<? super T> cons){
        if (isPresent()){
            cons.accept(value);
        }
    }

    public <R> Maybe<R> map(java.util.function.Function<? super T, ? extends R> func){
        if(isPresent()){
            R result = func.apply(value);
            return Maybe.of(result);
        }
        return Maybe.of(null);
    }

    public T get() throws NoSuchElementException{
        if(isPresent()){
            return value;
        }
        throw new NoSuchElementException(" maybe is empty");
    }

    public T orElse(T defVal){
        if(isPresent()){
            return value;
        }
        else{
            return defVal;
        }
    }

    public Maybe<T> filter(java.util.function.Predicate<? super T> pred){
        if(!isPresent()){
            return this;
        }

        if (pred.test(value)){
            return this;
        }
        else{
            return Maybe.of(null);
        }
    }
}
