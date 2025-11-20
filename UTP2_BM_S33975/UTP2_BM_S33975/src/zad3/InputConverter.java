package zad3;

import java.util.function.Function;

public class InputConverter <T>{
    private T input;
    public InputConverter(T input){
        this.input = input;
    }

    @SuppressWarnings("unchecked")
    public <R> R convertBy(Function... functions){
        Object result = input;
        for (Function f: functions) {
            result = f.apply(result);
        }
        return (R) result;
    }
}
