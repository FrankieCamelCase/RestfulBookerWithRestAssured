package com.restfulbooker.api.tests;

import com.restfulbooker.api.helpers.RestfulBookerHelper;
import com.restfulbooker.api.utils.ExtentLogger;
import com.restfulbooker.api.utils.ExtentReporter;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.given;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GetTests extends BaseTest {

    private RestfulBookerHelper helper = new RestfulBookerHelper();
    private Response response;
    private static int id;


    @Test
    @Order(1)
    public void healthCheck(){
        ExtentReporter.createTest("Health Check");
        response = helper.healthCheck();
        int statusCode = response.getStatusCode();

        Assertions.assertEquals(201, statusCode);
        ExtentLogger.pass("Status code '201' returned.");

    }

    @Test
    @Order(2)
    // This test returns all the current bookings in the DB.
    public void getAllBookings(){
        ExtentReporter.createTest("GET All Bookings");

        response = helper.getAllBookingIds();
        int responseCode = response.getStatusCode();

        JsonPath jpath = response.jsonPath();
        List<Integer> bookingIds = jpath.getList("bookingid");

        Assertions.assertFalse(bookingIds.isEmpty());
        ExtentLogger.pass("Booking Ids "+bookingIds+" successfully returned");

        Assertions.assertEquals(200, responseCode, "Response code should be '200'");
        ExtentLogger.pass("Status code '200' returned.");
    }

    @Test
    @Order(3)

    public void getOneBookingId(){
        ExtentReporter.createTest("GET One Booking Id From '/booking/id'");
        response = helper.getOneBookingIds();

        JsonPath jpath = response.jsonPath();
        int statusCode = response.getStatusCode();

        Assertions.assertEquals(200, statusCode, "Status code should be 200");
        ExtentLogger.pass("Status code '200' successfully returned.");

    }


}
