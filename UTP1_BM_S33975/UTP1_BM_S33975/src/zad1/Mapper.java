/**
 *
 *  @author Berlak Michał S33975
 *
 */

package zad1;


public interface Mapper<T, R> { // Uwaga: interfejs musi być sparametrtyzowany
    R map(T t);
}  
