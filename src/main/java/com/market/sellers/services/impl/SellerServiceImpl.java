package com.market.sellers.services.impl;

import com.market.sellers.documents.SellerDocument;
import com.market.sellers.exceptions.custom.BaseHttpException;
import com.market.sellers.exceptions.exceptionhandlers.ApiError;
import com.market.sellers.integrations.products.services.ProductsService;
import com.market.sellers.model.Seller;
import com.market.sellers.repositories.SellersRepository;
import com.market.sellers.services.SellerService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class SellerServiceImpl implements SellerService {
    private static final Logger log = LogManager.getLogger(SellerServiceImpl.class);

    @Autowired
    private SellersRepository sellersRepository;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private MessageSource messageSource;


    @Override
    public Seller save(Seller seller) {
        log.info("{} begin", seller.getName());
        Seller sellerSaved = this.sellersRepository.save(SellerDocument.build(seller)).convertToSeller();
        log.info("{} finished", seller.getId());

        return sellerSaved;
    }

    @Override
    public Seller edit(Seller seller) {
        log.info("{} begin", seller.getId());
        Optional<SellerDocument> document = this.sellersRepository.findById(seller.getId());

        if(document.isPresent()) {
            log.info("Seller found, finishing editing it {}", seller.getId());
            return this.sellersRepository.save(SellerDocument.build(seller)).convertToSeller();
        }
        else {
            throw new BaseHttpException(new ApiError(NOT_FOUND,
                    this.messageSource.getMessage("seller.not.found", null, Locale.getDefault())));
        }
    }

    @Override
    public Seller findById(String sellerId) {
        log.info("{} begin", sellerId);
        return this.sellersRepository.findById(sellerId).map(SellerDocument::convertToSeller)
                .orElseThrow(() -> new BaseHttpException(new ApiError(NOT_FOUND,
                        this.messageSource.getMessage("seller.not.found", null, Locale.getDefault()))));
    }

    @Override
    public List<Seller> findAll() {
        log.info("begin");
        List<SellerDocument> sellers = this.sellersRepository.findAllByOrderByNameAsc();

        if(!sellers.isEmpty()) {
            log.info("Found {} sellers", sellers.size());
            return sellers.stream().map(SellerDocument::convertToSeller).collect(Collectors.toList());
        }
        else {
            throw new BaseHttpException(new ApiError(NOT_FOUND,
                    this.messageSource.getMessage("seller.not.found", null, Locale.getDefault())));
        }
    }

    @Override
    public void delete(String sellerId) {
        log.info("{} sellerId begin", sellerId);
        this.productsService.deleteProducts(sellerId);
        log.info("Deleted products belonging to {}", sellerId);

        this.sellersRepository.deleteById(sellerId);
        log.info("Deleted {} seller", sellerId);
    }
}
