package com.restfulbooker.api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class BookingBody {

    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("totalprice")
    private int totalprice;

    @JsonProperty("depositpaid")
    private boolean depostipaid;

    @JsonProperty("bookingdates")
    private Bookingdates bookingdates;

    @JsonProperty("additionalneeds")
    private String additionalneeds;

}
