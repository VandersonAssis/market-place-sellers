package com.market.sellers.controllers;

import com.google.gson.Gson;
import com.market.sellers.exceptions.exceptionhandlers.ExceptionHandlers;
import com.market.sellers.model.Seller;
import com.market.sellers.services.SellerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.market.sellers.TestDataBuilder.buildSeller;
import static com.market.sellers.TestDataBuilder.buildSellersList;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class SellerControllerTest {
    @InjectMocks
    private SellerController sellerController;

    @Mock
    private SellerService sellerService;

    private MockMvc mockMvc;
    private String apiPrefix;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.sellerController)
                .setControllerAdvice(new ExceptionHandlers())
                .build();

        this.apiPrefix = "/marketplace/api/v1";
    }

    @Test
    public void shouldSaveSellerAndReturnOk() throws Exception {
        Seller seller = buildSeller();
        when(this.sellerService.save(any(Seller.class))).thenReturn(seller);

        this.mockMvc.perform(post(this.apiPrefix + "/sellers")
            .contentType(APPLICATION_JSON).content(new Gson().toJson(seller))).andExpect(status().isOk());
    }

    @Test
    public void shouldTryToSaveInvalidSellerAndReturnBadRequest() throws Exception {
        Seller seller = new Seller();
        when(this.sellerService.save(any(Seller.class))).thenReturn(seller);

        this.mockMvc.perform(post(this.apiPrefix + "/sellers")
                .contentType(APPLICATION_JSON).content(new Gson().toJson(seller))).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldFindByIdAndReturnOk() throws Exception {
        when(this.sellerService.findById(anyString())).thenReturn(buildSeller());

        this.mockMvc.perform(get(this.apiPrefix + "/sellers/test_seller_id"))
            .andExpect(status().isOk());
    }

    @Test
    public void shouldFindByIdAndReturnBody() throws Exception {
        when(this.sellerService.findById(anyString())).thenReturn(buildSeller());
        String expectedJson = new Gson().toJson(buildSeller());

        String responseJson = this.mockMvc.perform(get(this.apiPrefix + "/sellers/test_seller_id"))
                .andReturn().getResponse().getContentAsString();

        assertEquals(expectedJson, responseJson);
    }

    @Test
    public void shouldFindAllAndReturnOk() throws Exception {
        when(this.sellerService.findAll()).thenReturn(buildSellersList());

        this.mockMvc.perform(get(this.apiPrefix + "/sellers"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldFindAllAndReturnValidBody() throws Exception {
        when(this.sellerService.findAll()).thenReturn(buildSellersList());
        String expectedJson = new Gson().toJson(buildSellersList());

        String responseJson = this.mockMvc.perform(get(this.apiPrefix + "/sellers"))
                .andReturn().getResponse().getContentAsString();

        assertEquals(expectedJson, responseJson);
    }

    @Test
    public void shouldDeleteAndReturnNoContent() throws Exception {
        doNothing().when(this.sellerService).delete(anyString());

        this.mockMvc.perform(delete(this.apiPrefix + "/sellers/seller_test_id"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldUpdateSellerAndReturnNoContent() throws Exception {
        Seller seller = buildSeller();
        when(this.sellerService.edit(any(Seller.class))).thenReturn(seller);

        this.mockMvc.perform(put(this.apiPrefix + "/sellers/")
            .contentType(APPLICATION_JSON).content(new Gson().toJson(seller)))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldUpdateSellerAndReturnBadRequest() throws Exception {
        Seller seller = buildSeller();
        when(this.sellerService.edit(any(Seller.class))).thenReturn(seller);

        this.mockMvc.perform(put(this.apiPrefix + "/sellers/")
                .contentType(APPLICATION_JSON).content(""))
                .andExpect(status().isBadRequest());
    }
}