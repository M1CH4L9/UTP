//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package zad1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

class ListCreator<String> {
    private List<String> source;

    private ListCreator(List<String> source) {
        this.source = new ArrayList(source);
    }

    public static <String> ListCreator<String> collectFrom(List<String> list) {
        return new ListCreator<String>(list);
    }

    public ListCreator<String> when(Predicate<String> pred) {
        List<String> output_tmp = new ArrayList();

        for(String elem : this.source) {
            if (pred.test(elem)) {
                output_tmp.add(elem);
            }
        }

        this.source = output_tmp;
        return this;
    }

    public List<String> mapEvery(Function<String, String> map) {
        List<String> output_tmp = new ArrayList();

        for(String elem : this.source) {
            output_tmp.add(map.apply(elem));
        }

        return output_tmp;
    }
}
