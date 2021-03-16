package tablecell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Sort {

    public static void main(String[] args) {
        String[] values = {"#FFFFFF", "#00FF00", "#0000FF", "#CC00FF"};
        
        Map<String, Integer> mappie = new HashMap<String, Integer>();
        
        
        
        
        
        System.out.println("-----------Tree Map Sort--------------");
        for (int i=0; i < values.length; i++)
        {
            mappie.put(values[i],i);
        }
        
        printMap(mappie);
        
        System.out.println("-------");
        
        LinkedHashMap<String, Integer> linkMap = sortHashMapByValues(mappie);
        
        printMap(linkMap);
        
        System.out.println("-------");
        
        System.out.println("\nSorted Map......By Key");
        Map<String, Integer> treeMap = new TreeMap<String, Integer>(mappie);
        
        printMap(treeMap);

    }
    
  //pretty print a map
    public static <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey()
                + " Value : " + entry.getValue());
        }
    }
    
    public static LinkedHashMap<String, Integer> sortHashMapByValues(
            Map<String, Integer> passedMap) {
        List<String> mapKeys = new ArrayList<>(passedMap.keySet());
        List<Integer> mapValues = new ArrayList<>(passedMap.values());
        Collections.sort(mapValues);
        //Collections.sort(mapKeys);

        LinkedHashMap<String, Integer> sortedMap =
            new LinkedHashMap<>();

        Iterator<Integer> valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            int val = valueIt.next();
            Iterator<String> keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                String key = keyIt.next();
                int comp1 = passedMap.get(key);
                int comp2 = val;

                if (comp1 ==comp2) {
                    keyIt.remove();
                    sortedMap.put(key, val);
                    break;
                }
            }
        }
        return sortedMap;
    }



}
