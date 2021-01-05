package com.restfulbooker.api.tests;

import com.restfulbooker.api.helpers.RestfulBookerHelper;
import io.qameta.allure.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import java.util.List;

import static io.restassured.RestAssured.given;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VerbTests extends BaseTest {

    private static final Logger logger = LogManager.getLogger(VerbTests.class);
    private RestfulBookerHelper helper = new RestfulBookerHelper();
    private Response response;
    private int id;
    private String token;

    @Test
    @Step("Health Check")
    @Order(1)
    @Description("Health Check | GET /ping")
    @Epic("EP001")
    @Feature("GET")
    @Severity(SeverityLevel.BLOCKER)
    public void healthCheckTest(){
        logger.debug("Performing Health Check test at GET /ping...");

        response = helper.healthCheck();
        int statusCode = response.getStatusCode();

        Assertions.assertEquals(201, statusCode);
        logger.debug("Health Check test successful!");
    }

    @Test
    @Step("Display All Bookings")
    @Order(2)
    @Description("Display All Bookings | GET /bookings")
    @Epic("EP001")
    @Feature("GET")
    @Severity(SeverityLevel.NORMAL)
    public void getAllBookingsTest(){
        logger.debug("Performing Display All Bookings test...");

        response = helper.getAllBookingIds();
        int responseCode = response.getStatusCode();
        JsonPath jpath = response.jsonPath();

        List<Integer> bookingIds = jpath.getList("bookingid");

        Assertions.assertFalse(bookingIds.isEmpty());
        logger.debug("Bookingids returned successfully!");
        Assertions.assertEquals(200, responseCode, "Response code should be '200'");
        logger.debug("200 status code successfully returned!");
    }

    @Test
    @Step("Display One Booking")
    @Order(3)
    @Description("Display One Booking | GET /booking/:id")
    @Epic("EP001")
    @Feature("GET")
    @Severity(SeverityLevel.NORMAL)
    public void getOneBookingIdTest(){
        logger.debug("Performing Display One Booking Test...");

        response = helper.getOneBookingIds();
        JsonPath jpath = response.jsonPath();
        int statusCode = response.getStatusCode();

        Assertions.assertEquals(200, statusCode, "Status code should be 200");
        logger.debug("Status code 200 successfully returned!");
    }

    @Test
    @Step("Generate Token")
    @Order(4)
    @Description("Generate Token | POST /auth")
    @Epic("EP001")
    @Feature("POST")
    @Severity(SeverityLevel.BLOCKER)
    public void generateTokenTest(){
        logger.debug("Performing Generate Token test...");

        token = helper.generateToken();
        Assertions.assertNotNull(token);
        logger.debug("Token successfully generated!");
    }

    @Test
    @Step("Create Booking")
    @Order(5)
    @Description("Create Booking| POST /booking")
    @Epic("EP001")
    @Feature("POST")
    @Severity(SeverityLevel.CRITICAL)
    public void createBookingTest(){
        logger.debug("Performing Create Booking test...");

        response = helper.createBooking();
        JsonPath jpath = response.jsonPath();
        int statusCode = response.statusCode();

        Assertions.assertEquals(200, statusCode);
        logger.debug("200 status code returned!");
        Assertions.assertEquals(jpath.getString("booking.firstname"), helper.getFirstName());
        logger.debug("firstname created successfully!");
        Assertions.assertEquals(jpath.getString("booking.lastname"), helper.getLastName());
        logger.debug("lastname created successfully!");
        Assertions.assertEquals(jpath.getInt("booking.totalprice"),helper.getTotalprice());
        logger.debug("totalprice created successfully!");
        Assertions.assertEquals(jpath.getBoolean("booking.depositpaid"), helper.isDepositpaid());
        logger.debug("depositpaid created successfully!");
        Assertions.assertEquals(jpath.getString("booking.bookingdates.checkin"), helper.getCheckin());
        logger.debug("checkin created successfully!");
        Assertions.assertEquals(jpath.getString("booking.bookingdates.checkout"), helper.getCheckout());
        logger.debug("checkout created successfully!");
        Assertions.assertEquals(jpath.getString("booking.additionalneeds"), helper.getAdditionalneeds());
        logger.debug("additional needs created successfully!");
    }

    @Test
    @Step("Update Booking")
    @Order(6)
    @Description("Update Booking | PATCH /:id")
    @Epic("EP001")
    @Feature("PATCH")
    @Severity(SeverityLevel.CRITICAL)
    public void updateBookingTest(){
        logger.debug("Performing Update Booking test...");

        response = helper.updateBooking();
        JsonPath jpath = response.jsonPath();
        int responseCode = response.getStatusCode();

        Assertions.assertEquals(200, responseCode);
        logger.debug("200 status code received!");
        Assertions.assertEquals(helper.getFirstName(), jpath.getString("firstname"));
        logger.debug("firstname updated successfully!");
        Assertions.assertEquals(helper.getLastName(), jpath.getString("lastname"));
        logger.debug("lastname updated successfully!");
    }

    @Test
    @Step("Delete Booking")
    @Order(7)
    @Description("Delete Booking | DELETE /:id")
    @Epic("EP001")
    @Feature("DELETE")
    @Severity(SeverityLevel.NORMAL)
    public void deleteBookingTest(){
        logger.debug("Performing Delete Booking Test..");

        token = helper.generateToken();
        id = helper.getOneIdInDatabase();

        response = helper.deleteBooking(id);

        int responseCode = response.getStatusCode();
        Assertions.assertEquals(201, responseCode, "Response code should be '200'...");

        response = helper.verifyDeletionOfBookingId(id);
        Assertions.assertEquals(404, response.getStatusCode(), "Response code of deleted resource should be '404'...");

    }
}

