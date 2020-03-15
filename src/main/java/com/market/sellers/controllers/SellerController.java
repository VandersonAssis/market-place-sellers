package com.market.sellers.controllers;

import com.market.sellers.api.SellersApi;
import com.market.sellers.model.Seller;
import com.market.sellers.model.SellerListResponse;
import com.market.sellers.services.SellerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class SellerController extends BaseController implements SellersApi {
    private static final Logger log = LogManager.getLogger(SellerController.class);

    @Autowired
    private SellerService sellerService;

    @Override
    public ResponseEntity<Seller> saveSeller(@Valid Seller seller) {
        log.info("name {} begin", seller.getName());
        seller.setId(null);
        Seller sellerSaved = this.sellerService.save(seller);
        log.info("id {} seller saved", sellerSaved.getId());
        return new ResponseEntity<>(sellerSaved, OK);
    }

    @Override
    public ResponseEntity<Seller> findById(String idSeller) {
        log.info("{} begin", idSeller);
        return new ResponseEntity<>(this.sellerService.findById(idSeller), OK);
    }

    @Override
    public ResponseEntity<SellerListResponse> findAll() {
        log.info("begin");
        List<Seller> sellers = this.sellerService.findAll();
        SellerListResponse sellerListResponse = new SellerListResponse();
        sellerListResponse.addAll(sellers);
        log.info("Found {} sellers", sellers.size());

        return new ResponseEntity<>(sellerListResponse, OK);
    }

    @Override
    public ResponseEntity<Void> deleteSeller(String idSeller) {
        log.info("id {} begin", idSeller);
        this.sellerService.delete(idSeller);
        log.info("id {} deleted", idSeller);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> updateSeller(@Valid Seller seller) {
        log.info("id {} begin", seller.getId());
        this.sellerService.edit(seller);
        log.info("id {} updated", seller.getId());
        return new ResponseEntity<>(NO_CONTENT);
    }
}