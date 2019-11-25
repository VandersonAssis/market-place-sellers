package com.market.sellers.services;

import com.market.sellers.model.Seller;

import java.util.List;

public interface SellerService {
    Seller save(Seller seller);
    Seller edit(Seller seller);
    Seller findById(String sellerId);
    List<Seller> findAll();
    void delete(String sellerId);
}
