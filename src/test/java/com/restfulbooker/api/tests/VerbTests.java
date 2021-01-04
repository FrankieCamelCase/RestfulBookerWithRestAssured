package com.restfulbooker.api.tests;

import com.restfulbooker.api.helpers.RestfulBookerHelper;
import io.qameta.allure.*;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import java.util.List;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VerbTests extends BaseTest {

    private RestfulBookerHelper helper;
    private int id;
    private String token;

    @Test
    @Step("Health Check")
    @Order(1)
    @Description("Health Check | GET /ping")
    @Epic("EP001")
    @Feature("GET")
    @Severity(SeverityLevel.BLOCKER)
    public void healthCheck(){

        Response response = helper.healthCheck();
        int statusCode = response.getStatusCode();

        Assertions.assertEquals(201, statusCode);
    }

    @Test
    @Step("Display All Bookings")
    @Order(2)
    @Description("Display All Bookings | GET /bookings")
    @Epic("EP001")
    @Feature("GET")
    @Severity(SeverityLevel.NORMAL)
    public void getAllBookings(){

        Response response = helper.getAllBookingIds();
        int responseCode = response.getStatusCode();

        JsonPath jpath = response.jsonPath();
        List<Integer> bookingIds = jpath.getList("bookingid");

        Assertions.assertFalse(bookingIds.isEmpty());
        Assertions.assertEquals(200, responseCode, "Response code should be '200'");
    }

    @Test
    @Step("Display One Booking")
    @Order(3)
    @Description("Display One Booking | GET /booking/:id")
    @Epic("EP001")
    @Feature("GET")
    @Severity(SeverityLevel.NORMAL)
    public void getOneBookingId(){

        Response response = helper.getOneBookingIds();

        JsonPath jpath = response.jsonPath();
        int statusCode = response.getStatusCode();

        Assertions.assertEquals(200, statusCode, "Status code should be 200");
    }

    @Test
    @Step("Generate Token")
    @Order(4)
    @Description("Generate Token | POST /auth")
    @Epic("EP001")
    @Feature("POST")
    @Severity(SeverityLevel.BLOCKER)
    public void generateToken(){
        token = helper.generateToken();
        Assertions.assertNotNull(token);
    }

    @Test
    @Step("Generate Token")
    @Order(5)
    @Description("Generate Token | POST /auth")
    @Epic("EP001")
    @Feature("POST")
    @Severity(SeverityLevel.CRITICAL)
    public void createBooking(){

        Response response = helper.createBooking();
        response.prettyPrint();
        JsonPath jpath = response.jsonPath();
        int statusCode = response.statusCode();

        Assertions.assertEquals(200, statusCode);
        Assertions.assertEquals(jpath.getString("booking.firstname"), helper.getFirstName());
        Assertions.assertEquals(jpath.getString("booking.lastname"), helper.getLastName());
        Assertions.assertEquals(jpath.getInt("booking.totalprice"),helper.getTotalprice());
        Assertions.assertEquals(jpath.getBoolean("booking.depositpaid"), helper.isDepositpaid());
        Assertions.assertEquals(jpath.getString("booking.bookingdates.checkin"), helper.getCheckin());
        Assertions.assertEquals(jpath.getString("booking.bookingdates.checkout"), helper.getCheckout());
        Assertions.assertEquals(jpath.getString("booking.additionalneeds"), helper.getAdditionalneeds());
    }
}

