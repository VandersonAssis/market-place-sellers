package com.market.sellers.integrations.products.services.impl;

import com.market.sellers.exceptions.custom.BaseHttpException;
import com.market.sellers.exceptions.exceptionhandlers.ApiError;
import com.market.sellers.integrations.products.ProductsApiProxy;
import com.market.sellers.integrations.products.services.ProductsService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ProductsServiceImpl implements ProductsService {
    @Autowired
    private ProductsApiProxy productsApiProxy;

    @Override
    public void deleteProducts(String idSeller) {
        try {
            this.productsApiProxy.deleteProducts(idSeller);
        } catch(FeignException ex) {
            if(ex.status() != NOT_FOUND.value())
                throw new BaseHttpException(new ApiError(INTERNAL_SERVER_ERROR, "Error while trying to delete seller products"));
        }
    }
}
