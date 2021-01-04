package com.restfulbooker.api.helpers;

import com.restfulbooker.api.constants.Endpoints;
import com.restfulbooker.api.pojo.TokenBody;
import com.restfulbooker.api.utils.AuthUtil;
import com.restfulbooker.api.utils.DataGenerator;
import io.qameta.allure.*;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class RestfulBookerHelper {

    private static int id;
    private static String token;

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

    public String generateToken(){
        TokenBody tokenBody = AuthUtil.getTokenBodyPojo();

        //System.out.println(tokenBody);

        token = given()
                .contentType(ContentType.JSON)
                .body(tokenBody)
                .when()
                .post(Endpoints.AUTH)
                .path("token");

        return token;
    }

}
