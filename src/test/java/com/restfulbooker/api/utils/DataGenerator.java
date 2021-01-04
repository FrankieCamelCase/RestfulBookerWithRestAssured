package com.restfulbooker.api.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataGenerator {

    private static Random r = new Random();

    public static int getRandom(List<Integer> list){
        return (int)(Math.random()*list.size());
    }
}
