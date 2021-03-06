package com.restfulbooker.api.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class TokenBody {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

}
