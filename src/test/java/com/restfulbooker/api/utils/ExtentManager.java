package com.restfulbooker.api.utils;

import com.aventstack.extentreports.ExtentTest;

public class ExtentManager {

    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

    static ExtentTest getExtentTest(){ // default --> only accessible from within package
        return extentTest.get();
    }

    static void setExtentTest(ExtentTest test){
        extentTest.set(test);
    }

    static void unload(){
        extentTest.remove();
    }

}
