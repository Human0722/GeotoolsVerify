package io.human0722.hutools;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xueliang
 * @description TODO
 * @date 2022-03-11 13:41
 */
public class SortMap {
    public static void main(String[] args) {
        HashMap<String, Integer> result = new HashMap<>();
        HashMap<String, Integer> resultByValue = new HashMap<>();
        HashMap<String, Integer> map = new HashMap<>();
        map.put("a",100);
        map.put("c",300);
        map.put("k",30);
        map.put("t",1);
        map.put("p",110);
        outputMap(map);
        map.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEachOrdered(x->result.put(x.getKey(), x.getValue()));
        outputMap(result);
    }

    public static void outputMap(Map map) {
        map.entrySet().stream().forEach(System.out::println);
    }
}
