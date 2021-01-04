package com.restfulbooker.api.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.aventstack.extentreports.reporter.configuration.ViewName;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class ExtentReporter {
    private static ExtentReports extent;

    private ExtentReporter(){}

    public static void initReports() {
        if(Objects.isNull(extent)) {
            extent = new ExtentReports();
            ExtentSparkReporter spark = new ExtentSparkReporter("target/apiTestReport.html");
            extent.attachReporter(spark);
            spark.config().setTheme(Theme.DARK);
            spark.config().setDocumentTitle("FrankieCameCase's Report");
            spark.config().setReportName("RestfulBooker API");
        }
    }

    public static void createTest(String testCaseName){
        ExtentTest test = extent.createTest(testCaseName);
        ExtentManager.setExtentTest(test);
    }

    public static void flushReports(){
        if(Objects.nonNull(extent)) {
            extent.flush();
        }
    }

}
