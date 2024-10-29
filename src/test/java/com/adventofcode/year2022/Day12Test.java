package com.adventofcode.year2022;
import static org.junit.Assert.assertEquals;

import com.google.gson.Gson;
import org.junit.Test;

public class Day12Test {
    @Test
    public void testExample(){
        Gson gson = new Gson();
        var o = gson.fromJson("[1,2,3]", Object.class);
    assertEquals(6, com.adventofcode.year2015.Day12.sumJsonLeafs(o));
    }

    @Test
    public void testExample2(){
        Gson gson = new Gson();
        var o = gson.fromJson("{\"a\":2,\"b\":4}", Object.class);
        assertEquals(6, com.adventofcode.year2015.Day12.sumJsonLeafs(o));
    }

    @Test
    public void testExample3(){
        Gson gson = new Gson();
        var o = gson.fromJson("[[[3]]]", Object.class);
        assertEquals(3, com.adventofcode.year2015.Day12.sumJsonLeafs(o));
    }

    @Test
    public void testExample4(){
        Gson gson = new Gson();
        var o = gson.fromJson("{\"a\":{\"b\":4},\"c\":-1}", Object.class);
        assertEquals(3, com.adventofcode.year2015.Day12.sumJsonLeafs(o));
    }

    @Test
    public void testExample5(){
        Gson gson = new Gson();
        var o = gson.fromJson("{\"a\":[-1,1]}", Object.class);
        assertEquals(0, com.adventofcode.year2015.Day12.sumJsonLeafs(o));
    }

    @Test
    public void testExample6(){
        Gson gson = new Gson();
        var o = gson.fromJson("[-1,{\"a\":1}]", Object.class);
        assertEquals(0, com.adventofcode.year2015.Day12.sumJsonLeafs(o));
    }

    @Test
    public void testExample7(){
        Gson gson = new Gson();
        var o = gson.fromJson("[]", Object.class);
        assertEquals(0, com.adventofcode.year2015.Day12.sumJsonLeafs(o));
    }

    @Test
    public void testExample8(){
        Gson gson = new Gson();
        var o = gson.fromJson("{}", Object.class);
        assertEquals(0, com.adventofcode.year2015.Day12.sumJsonLeafs(o));
    }
}

