package com.nickolasfisher;

import static org.junit.Assert.assertTrue;

import io.restassured.RestAssured;
import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

public class AppTest {

    static {
        RestAssured.requestSpecification = RestAssured.with()
                .filter(new Filter() {
            @Override
            public Response filter(FilterableRequestSpecification filterableRequestSpecification,
                                   FilterableResponseSpecification filterableResponseSpecification,
                                   FilterContext filterContext) {
                StopWatch sw = new StopWatch();
                sw.start();
                Response response = filterContext.next(filterableRequestSpecification, filterableResponseSpecification);
                sw.stop();
                System.out.println("time: " + sw.getTime());
                return response;
            }
        });
    }

    @Test
    public void getGoogleHomepage() {
        RestAssured.with()
                .baseUri("https://www.google.com")
                .get()
                .then()
                .statusCode(200);
    }

    @Test
    public void getDuckDuckGoHomepage() {
        RestAssured.with()
                .baseUri("https://duckduckgo.com")
                .get()
                .then()
                .statusCode(200);
    }
}
