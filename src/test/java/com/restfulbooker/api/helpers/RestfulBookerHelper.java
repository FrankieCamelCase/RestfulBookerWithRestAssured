package com.restfulbooker.api.helpers;

import com.restfulbooker.api.Constants.Endpoints;
import com.restfulbooker.api.utils.DataGenerator;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class RestfulBookerHelper {

    private static int id;

    public Response getAllBookingIds(){

        Response response = given()
                .when()
                .get(Endpoints.BOOKING);

        JsonPath jpath = response.jsonPath();
        List<Integer> bookingIds = jpath.getList("bookingid");
        id = DataGenerator.getRandom(bookingIds);
        return response;
    }

    public Response getOneBookingIds(){
        return given()
                .log().all()
                .when()
                .get(Endpoints.BOOKING+"/"+id).prettyPeek();
    }

    public int getId(){
        return id;
    }

    public Response healthCheck(){
        return given()
                .get(Endpoints.PING);
    }

}
