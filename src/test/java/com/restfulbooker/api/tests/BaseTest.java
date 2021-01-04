package com.restfulbooker.api.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import com.restfulbooker.api.utils.ConfigurationReader;
import com.restfulbooker.api.utils.ExtentReporter;

import java.io.IOException;

public class BaseTest {

    @BeforeAll
    public static void setup(){
        ExtentReporter.initReports();
        RestAssured.baseURI = ConfigurationReader.getProperty("baseUri");
    }


    @AfterAll
    public static void teardown() throws IOException {
        ExtentReporter.flushReports();
    }
}
