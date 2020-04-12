package com.market.sellers.integrations.products.services.impl;

import com.market.sellers.exceptions.custom.BaseHttpException;
import com.market.sellers.integrations.products.ProductsApiProxy;
import com.market.sellers.integrations.products.services.ProductsService;
import feign.FeignException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.OK;

@RunWith(SpringRunner.class)
public class ProductsServiceImplTest {
    private ProductsService productsService;

    @Mock
    private ProductsApiProxy productsApiProxy;

    @Mock
    private FeignException.FeignServerException feignServerException;

    @Mock
    private MessageSource msg;

    @Before
    public void setUp() {
        this.productsService = new ProductsServiceImpl();
        ReflectionTestUtils.setField(this.productsService, "productsApiProxy", this.productsApiProxy);
        ReflectionTestUtils.setField(this.productsService, "msg", this.msg);
    }

    @Ignore
    @Test
    public void deleteProductsShouldCallProductsApiProxyDeleteProducts() {
        when(this.productsApiProxy.deleteProducts(anyString())).thenReturn(new ResponseEntity<>(OK));
        this.productsService.deleteProducts("test_id_seller");

        verify(this.productsApiProxy, times(1)).deleteProducts(anyString());
    }

    @Ignore
    @Test(expected = BaseHttpException.class)
    public void deleteProductsShouldConvertFeignExceptionIntoBaseHttpException() {
        when(this.productsApiProxy.deleteProducts(anyString())).thenThrow(this.feignServerException);
        this.productsService.deleteProducts("test_id_seller");
    }
}