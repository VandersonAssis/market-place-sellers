package com.market.sellers.integrations.products;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("${products-service.url}")
public interface ProductsApiProxy {
    @DeleteMapping("/marketplace/api/v1/products/{idSeller}/seller")
    ResponseEntity<Void> deleteProducts(@PathVariable("idSeller") String idSeller);
}
