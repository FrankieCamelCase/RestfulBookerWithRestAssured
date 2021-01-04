package com.restfulbooker.api.helpers;

import com.restfulbooker.api.constants.Endpoints;
import com.restfulbooker.api.pojo.BookingBody;
import com.restfulbooker.api.pojo.Bookingdates;
import com.restfulbooker.api.pojo.TokenBody;
import com.restfulbooker.api.utils.AuthUtil;
import com.restfulbooker.api.utils.DataGenerator;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.Getter;

import java.util.List;

import static io.restassured.RestAssured.given;


public class RestfulBookerHelper {
    @Getter
    private static int id;
    @Getter
    private static String firstName = DataGenerator.getRandomFirstName();
    @Getter
    private static String lastName = DataGenerator.getRandomLastName();
    @Getter
    private static int totalprice = DataGenerator.getRandomTotalPrice();
    @Getter
    private static boolean depositpaid = DataGenerator.getRandomBoolean();
    @Getter
    private static String checkin = DataGenerator.createRandomDate(2021).toString();
    @Getter
    private static String checkout = DataGenerator.createRandomDate(2022).toString();
    @Getter
    private static String additionalneeds = DataGenerator.getRandomObject();

    public Response getAllBookingIds(){
        Response response = given()
                .when()
                .get(Endpoints.BOOKING);

        JsonPath jpath = response.jsonPath();
        List<Integer> bookingIds = jpath.getList("bookingid");
        id = DataGenerator.getRandomIndexFromList(bookingIds);

        return response;
    }

    public Response getOneBookingIds(){
        return given()
                .log().all()
                .when()
                .get(Endpoints.BOOKING+"/"+id);
    }

    public int getId(){
        return id;
    }

    public Response healthCheck(){
        return given()
                .get(Endpoints.PING);
    }

    public String generateToken(){
        TokenBody tokenBody = AuthUtil.getTokenBodyPojo();

        return given()
                .contentType(ContentType.JSON)
                .body(tokenBody)
                .when()
                .post(Endpoints.AUTH)
                .path("token");
    }

    public Response createBooking(){

        Bookingdates bookingDates = new Bookingdates();
        bookingDates.setCheckin(checkin);
        bookingDates.setCheckout(checkout);

        BookingBody bookingBody = new BookingBody();
        bookingBody.setFirstname(firstName);
        bookingBody.setLastname(lastName);
        bookingBody.setTotalprice(totalprice);
        bookingBody.setDepostipaid(depositpaid);
        bookingBody.setBookingdates(bookingDates);
        bookingBody.setAdditionalneeds(additionalneeds);

        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .accept("application/json")
                .body(bookingBody)
                .when()
                .post(Endpoints.BOOKING).prettyPeek();
    }


}
