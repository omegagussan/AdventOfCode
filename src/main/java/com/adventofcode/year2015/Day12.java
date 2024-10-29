package com.adventofcode.year2015;

import com.google.gson.Gson;

import java.io.InputStream;
import java.util.*;

public class Day12 {
    public static void main(String[] args) {
        try {
            InputStream i = Day12.class.getClassLoader().getResourceAsStream("2015/day12.json");
            Scanner s = new Scanner(i).useDelimiter(System.lineSeparator());
            StringBuilder jsonString = new StringBuilder();
            while (s.hasNextLine()) {
                jsonString.append(s.nextLine());
            }
            s.close();
            Gson gson = new Gson();
            Object o = gson.fromJson(jsonString.toString(), Object.class);
            System.out.println(sumJsonLeafsIncludingRed(o));
            System.out.println(sumJsonLeafsExcludeRed(o));

        } catch (Exception e) {
            System.err.println("Something went poorly");
            e.printStackTrace();
        }
    }

    public static int sumJsonLeafsIncludingRed(Object o) {
        return sumJson(o, false);
    }

    public static int sumJsonLeafsExcludeRed(Object o) {
        return sumJson(o, true);
    }

    public static int sumJson(Object o, boolean ignoreRed) {
        if (o instanceof Double d) {
            return d.intValue();
        } else if (o instanceof List) {
            int sum = 0;
            for (Object i : (List) o) {
                sum += sumJson(i, ignoreRed);
            }
            return sum;
        } else if (o instanceof Map) {
            int sum = 0;
            for (Object i : ((Map) o).values()) {
                if (ignoreRed && i instanceof String s && s.equals("red")) {
                    return 0;
                }
                sum += sumJson(i, ignoreRed);
            }
            return sum;
        }
        return 0;
    }
}