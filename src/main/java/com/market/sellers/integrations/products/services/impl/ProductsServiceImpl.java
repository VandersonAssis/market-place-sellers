package com.market.sellers.integrations.products.services.impl;

import com.market.sellers.exceptions.custom.BaseHttpException;
import com.market.sellers.exceptions.exceptionhandlers.ApiError;
import com.market.sellers.integrations.products.ProductsApiProxy;
import com.market.sellers.integrations.products.services.ProductsService;
import feign.FeignException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ProductsServiceImpl implements ProductsService {
    private static final Logger log = LogManager.getLogger(ProductsServiceImpl.class);

    @Autowired
    private MessageSource msg;

//    @Autowired
//    private ProductsApiProxy productsApiProxy;

    @Override
    public void deleteProducts(String idSeller) {
//        try {
//            log.info("deleteProducts by idSeller {} begin", idSeller);
//            this.productsApiProxy.deleteProducts(idSeller);
//            log.info("deleteProducts by idSeller {} finished", idSeller);
//        } catch(FeignException ex) {
//            if(ex.status() != NOT_FOUND.value()) {
//                throw new BaseHttpException(new ApiError(INTERNAL_SERVER_ERROR,
//                        this.msg.getMessage("error.deleting.seller.products", null, Locale.getDefault())));
//            }
//        }
    }
}
