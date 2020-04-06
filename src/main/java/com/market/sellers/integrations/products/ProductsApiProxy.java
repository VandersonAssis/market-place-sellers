package com.market.sellers.integrations.products;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${api-gateway.application.name}")
@RibbonClient(name = "${products.spring.application.name}")
public interface ProductsApiProxy {
    @DeleteMapping("/${products.spring.application.name}/marketplace/api/v1/products/{idSeller}/seller")
    ResponseEntity<Void> deleteProducts(@PathVariable("idSeller") String idSeller);
}
