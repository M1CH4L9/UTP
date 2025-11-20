package zad2;

import java.util.ArrayList;
import java.util.*;
import java.util.function.*;
import java.io.*;

public class XList<T> extends ArrayList<T> {

    public XList(T... elems) {
        Collections.addAll(this, elems);
    }

    public XList(Collection<T> coll){
        super(coll);
    }

    public static <T> XList<T> of(T... elems){
        return new XList<>(elems);
    }

    public static <T> XList<T> of(Collection<T> coll){
        return new XList<>(coll);
    }

    public static XList <String> charsOf(String s){
        XList<String> result = new XList<>();

        for(char c : s.toCharArray()){
            result.add(String.valueOf(c));
        }
        return result;
    }

    public static XList<String> tokensOf(String s){
        XList<String> result = new XList<>();

        StringTokenizer st = new StringTokenizer(s);
        while (st.hasMoreTokens()){
            result.add(st.nextToken());
        }
        return result;
    }

    public static XList<String> tokensOf(String s, String sep){
        XList<String> result = new XList<>();

        StringTokenizer st = new StringTokenizer(s, sep);
        while (st.hasMoreTokens()){
            result.add(st.nextToken());
        }
        return result;
    }

    public XList<T> union(Collection<T> coll){
        XList<T> result = new XList<>(this);
        result.addAll(coll);
        return result;
    }

    public XList<T> union(T... elems){
        XList<T> result = new XList<>(this);
        Collections.addAll(result, elems);
        return result;
    }

    public XList<T> diff(Collection<T> coll){
        XList<T> result = new XList<>(this);
        result.removeAll(coll);
        return result;
    }

    public XList<T> unique(){
        return new XList<>(new LinkedHashSet<>(this));
    }

    public String join(){
        return join("");
    }

    public String join(String sep){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<this.size();i++){
            sb.append(this.get(i).toString());
            if(i<this.size()-1){
                sb.append(sep);
            }
        }
        return sb.toString();
    }

    public <R> XList<R> collect(Function<T,R> func){
        XList<R> result = new XList<>();
        for(T elem:this){
            result.add(func.apply(elem));
        }
        return result;
    }

    public void forEachWithIndex(BiConsumer<T,Integer> consumer){
        for(int i=0;i<this.size();i++){
            consumer.accept(this.get(i),i);
        }
    }

    public XList combine(){
        if(this.isEmpty()){
            XList<XList> emptyResult = new XList<>();
            emptyResult.add(new XList<>());
            return emptyResult;
        }

        Collection head = (Collection)this.get(0);
        XList tail = new XList<>(this.subList(1,this.size()));
        XList<XList> tailCombins = tail.combine();
        XList<XList> result = new XList<>();

        for (XList combinFromTail : tailCombins) {
            for(Object elemFromHead: head){
                XList newCombin = new XList<>();
                newCombin.add(elemFromHead);
                newCombin.addAll(combinFromTail);
                result.add(newCombin);
            }
        }
        return result;
    }
}
