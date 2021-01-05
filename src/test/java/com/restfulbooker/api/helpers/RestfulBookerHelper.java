package com.restfulbooker.api.helpers;

import com.restfulbooker.api.constants.Endpoints;
import com.restfulbooker.api.pojo.BookingBody;
import com.restfulbooker.api.pojo.BookingDates;
import com.restfulbooker.api.pojo.TokenBody;
import com.restfulbooker.api.utils.AuthUtil;
import com.restfulbooker.api.utils.DataGenerator;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

@Getter
public class RestfulBookerHelper {

    private int id;
    private static final Logger logger = LogManager.getLogger(RestfulBookerHelper.class);
    private String firstName = DataGenerator.getRandomFirstName();
    private String lastName = DataGenerator.getRandomLastName();
    private int totalprice = DataGenerator.getRandomTotalPrice();
    private boolean depositpaid = DataGenerator.getRandomBoolean();
    private String checkin = DataGenerator.createRandomDate(2021).toString();
    private String checkout = DataGenerator.createRandomDate(2022).toString();
    private String additionalneeds = DataGenerator.getRandomObject();

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
        id = getOneIdInDatabase();
        logger.debug("Getting bookingid number "+id+"from DB at GET /booking/"+id+"...");
        return given()
                .log().all()
                .when()
                .get(Endpoints.BOOKING+"/"+id);

    }

    public int getId(){
        return id;
    }

    public Response healthCheck(){
        logger.debug("Performing health check at GET /ping...");
        return given()
                .get(Endpoints.PING);
    }

    public String generateToken(){
        logger.debug("Generating token at POST /auth...");
        TokenBody tokenBody = AuthUtil.getTokenBodyPojo();

        String token = given()
                .contentType(ContentType.JSON)
                .body(tokenBody)
                .when()
                .post(Endpoints.AUTH)
                .path("token");

        logger.debug("Token is "+token);
        return token;
    }

    public Response createBooking(){
        logger.debug("Creating a booking at POST /booking/");
        BookingDates bookingDates = new BookingDates();
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
                .post(Endpoints.BOOKING);
    }

    public Response updateBooking(){
        id = getOneIdInDatabase();
        logger.debug("Updating bookingid at PATCH /booking/"+id);

        Map<String,String> map = new HashMap<>();
        map.put("firstname", firstName+DataGenerator.getRandomFirstName());
        map.put("lastname", lastName+DataGenerator.getRandomFirstName());

        firstName = map.get("firstname");
        lastName = map.get("lastname");

        return given()
                .auth().preemptive().basic("admin", "password123")
                .contentType(ContentType.JSON)
                .accept("application/json")
                .body(map)
                .when()
                .patch("/booking/"+id);

    }

    public List<Integer> getAllIdsInDatabase(){
        logger.debug("Getting all bookingids in DB at GET /booking");
        Response response = given()
                    .when()
                    .get("/booking");

        JsonPath jpath = response.jsonPath();

        List<Integer> allIdsInDatabase = new ArrayList<>();
        allIdsInDatabase = jpath.getList("bookingid");

        return allIdsInDatabase;
    }

    public int getOneIdInDatabase(){
        List<Integer> allIdsInDatabase = getAllIdsInDatabase();
        logger.debug("Getting random bookingid from bookingids at GET /booking");
        int randomIndex = DataGenerator.createRandomIntBetween(0, allIdsInDatabase.size());
        logger.debug("Random index is "+randomIndex);
        id = allIdsInDatabase.get(randomIndex);
        logger.debug("Random booking id is "+id);

        return id;
    }


}
