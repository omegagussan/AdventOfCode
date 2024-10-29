package com.adventofcode.year2015;

import com.google.gson.Gson;

import java.io.InputStream;
import java.util.*;

public class Day12 {
    public static void main(String[] args) {
        try {
            InputStream i = Day7.class.getClassLoader().getResourceAsStream("2015/day12.json");
            Scanner s = new Scanner(i).useDelimiter(System.lineSeparator());
            StringBuilder jsonString = new StringBuilder();
            while (s.hasNextLine()) {
                jsonString.append(s.nextLine());
            }
            s.close();
            Gson gson = new Gson();
            Object o = gson.fromJson(jsonString.toString(), Object.class);
            System.out.println(sumJsonLeafs(o));
        } catch (Exception e) {
            System.err.println("Something went poorly");
            e.printStackTrace();
        }
    }

    public static int sumJsonLeafs(Object o) {
        if (o instanceof Double d) {
            return d.intValue();
        } else if (o instanceof List) {
            int sum = 0;
            for (Object i : (List) o) {
                sum += sumJsonLeafs(i);
            }
            return sum;
        } else if (o instanceof Map) {
            int sum = 0;
            for (Object i : ((Map) o).values()) {
                sum += sumJsonLeafs(i);
            }
            return sum;
        }
        return 0;
    }
}