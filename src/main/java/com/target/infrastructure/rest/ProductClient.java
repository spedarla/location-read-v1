package com.target.infrastructure.rest;

import io.micronaut.http.HttpHeaders;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Header;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.client.annotation.Client;

@Client("https://redsky-uat.perf.target.com")
@Header(name = HttpHeaders.ACCEPT, value = MediaType.APPLICATION_JSON)
public interface ProductClient {
    @Get("/redsky_aggregations/v1/redsky/case_study_v1?key=3yUxt7WltYG7MFKPp7uyELi1K40ad2ys&tcin={id}")
    String getProductName(@PathVariable Long id);
}
