package com.market.sellers.controllers;

import com.market.sellers.api.SellersApi;
import com.market.sellers.model.Seller;
import com.market.sellers.model.SellerListResponse;
import com.market.sellers.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;

@RestController
public class SellerController extends BaseController implements SellersApi {
    @Autowired
    private SellerService sellerService;

    @Override
    public ResponseEntity<Seller> saveSeller(@Valid Seller seller) {
        seller.id(null);
        return new ResponseEntity<>(this.sellerService.save(seller), OK);
    }

    @Override
    public ResponseEntity<Seller> findById(String idSeller) {
        return new ResponseEntity<>(this.sellerService.findById(idSeller), OK);
    }

    @Override
    public ResponseEntity<SellerListResponse> findAll() {
        List<Seller> sellers = this.sellerService.findAll();
        SellerListResponse sellerListResponse = new SellerListResponse();
        sellerListResponse.addAll(sellers);

        return new ResponseEntity<>(sellerListResponse, OK);
    }

    @Override
    public ResponseEntity<Void> deleteSeller(String idSeller) {
        this.sellerService.delete(idSeller);
        return new ResponseEntity<>(NO_CONTENT);
    }

    @Override
    public ResponseEntity<Void> updateSeller(@Valid Seller seller) {
        this.sellerService.edit(seller);
        return new ResponseEntity<>(NO_CONTENT);
    }
}