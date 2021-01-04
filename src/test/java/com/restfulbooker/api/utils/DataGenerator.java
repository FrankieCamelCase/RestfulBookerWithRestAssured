package com.restfulbooker.api.utils;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class DataGenerator {

    static Faker fake = new Faker();

    public static LocalDate createRandomDate(int baseYear){
        int day = fake.number().numberBetween(1, 28);
        int month = fake.number().numberBetween(1, 12);
        return LocalDate.of(baseYear,month,day);
    }

    public static int createRandomIntBetween(int start, int end){
        return fake.number().numberBetween(start, end);
    }

    public static int getRandomIndexFromList(List<Integer> list){
        return (int)(Math.random()*list.size());
    }

    public static String getRandomObject(){
        return fake.commerce().productName();
    }

    public static String getRandomFirstName(){
        return fake.name().firstName();
    }

    public static String getRandomLastName() {
        return fake.name().lastName();
    }

    public static int getRandomTotalPrice(){
        return fake.number().numberBetween(0,1000);
    }

    public static boolean getRandomBoolean(){
        return fake.bool().bool();
    }

}
