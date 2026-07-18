package io.bluebeaker.mtepatches.utils;

import java.util.*;

public class Counter<T> {
    private final Map<T,Integer> values = new HashMap<>();
    public void add(T t){
        values.put(t, values.getOrDefault(t,0)+1);
    }
    public void clear(){
        values.clear();
    }
    public Map<T,Integer> getValues(){
        return values;
    }
    public List<Map.Entry<T,Integer>> sortedEntries(){
        List<Map.Entry<T, Integer>> list = new ArrayList<>(values.entrySet());
        list.sort(Map.Entry.comparingByValue());
        return list;
    }
    public int sum(){
        int sum = 0;
        for(Integer i : values.values()){
            sum += i;
        }
        return sum;
    }
}
