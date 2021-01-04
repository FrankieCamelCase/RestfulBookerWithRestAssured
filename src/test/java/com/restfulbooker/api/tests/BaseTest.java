package com.restfulbooker.api.tests;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import com.restfulbooker.api.utils.ConfigurationReader;

import java.io.IOException;

import static io.restassured.RestAssured.given;


public abstract class BaseTest {

    @BeforeAll
    public static void setup(){

        RestAssured.baseURI = ConfigurationReader.getProperty("baseUri");
        RestAssured.useRelaxedHTTPSValidation();
        RestAssured.filters(new io.qameta.allure.restassured.AllureRestAssured());

    }

    @AfterAll
    public static void teardown() {
        RestAssured.reset();
    }

}
