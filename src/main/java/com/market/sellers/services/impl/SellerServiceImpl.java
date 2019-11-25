package com.market.sellers.services.impl;

import com.market.exceptions.custom.ResourceNotFoundException;
import com.market.sellers.documents.SellerDocument;
import com.market.sellers.model.Seller;
import com.market.sellers.repositories.SellersRepository;
import com.market.sellers.services.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellersRepository sellersRepository;

    @Override
    public Seller save(Seller seller) {
        return this.sellersRepository.save(SellerDocument.build(seller)).convertToSeller();
    }

    @Override
    public Seller edit(Seller seller) {
        Optional<SellerDocument> document = this.sellersRepository.findById(seller.getId());

        if(document.isPresent())
            return this.sellersRepository.save(SellerDocument.build(seller)).convertToSeller();
        else
            throw new ResourceNotFoundException();
    }

    @Override
    public Seller findById(String sellerId) {
        return this.sellersRepository.findById(sellerId).map(SellerDocument::convertToSeller)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<Seller> findAll() {
        List<SellerDocument> sellers = this.sellersRepository.findAll();

        if(!sellers.isEmpty())
            return sellers.stream().map(SellerDocument::convertToSeller).collect(Collectors.toList());
        else
            throw new ResourceNotFoundException();
    }

    @Override
    public void delete(String sellerId) {
        this.sellersRepository.deleteById(sellerId);
    }
}
