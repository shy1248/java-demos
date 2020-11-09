package me.shy.demo.base;

import java.util.HashMap;
import java.util.Map;

public class TestArgsWords {
    //public static final Integer ONE = 1;
    public static final int ONE = 1;

    public static void main(String[] args) {
        Map<String, Integer> m = new HashMap<String, Integer>();
    /*
    for (int i=0; i<args.length; i++) {
      //Integer freq = (Integer) m.get(args[i]);
      int freq = m.get(args[i]) == null ? 0 : m.get(args[i]);
      m.put(args[i],(freq == 0 ? ONE :freq + 1));
    }
    */

        for (int i = 0; i < args.length; i++) {

            //if(!m.containsKey(args[i])) {
            if (m.get(args[i]) == null) {
                m.put(args[i], ONE);
            } else {
                int freq = m.get(args[i]);
                m.put(args[i], freq + 1);
            }
        }
        System.out.println(m.size() + " distinct words detected:");
        System.out.println(m);

    }
}