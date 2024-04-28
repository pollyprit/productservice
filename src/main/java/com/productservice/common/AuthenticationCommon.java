package com.productservice.common;

import com.productservice.dtos.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthenticationCommon {
    private RestTemplate restTemplate;
    @Value("${user.service.url}")
    private String USER_SERVICE_URL;

    public AuthenticationCommon(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // To validate the token from User-Service and get the associated User.
    public UserDto validateToken(String token) {
        ResponseEntity<UserDto> userDtoResponse =  restTemplate.postForEntity(
                getUserServiceUrl() + token, null, UserDto.class);

        if (userDtoResponse.getBody() == null)
            return null;

        return userDtoResponse.getBody();
    }

    private String getUserServiceUrl() {
        return "http://" + USER_SERVICE_URL + "/users/validate/";
    }
}
