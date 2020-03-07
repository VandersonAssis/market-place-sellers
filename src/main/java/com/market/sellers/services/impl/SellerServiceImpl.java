package com.market.sellers.services.impl;

import com.market.sellers.documents.SellerDocument;
import com.market.sellers.exceptions.custom.BaseHttpException;
import com.market.sellers.exceptions.exceptionhandlers.ApiError;
import com.market.sellers.integrations.products.services.ProductsService;
import com.market.sellers.model.Seller;
import com.market.sellers.repositories.SellersRepository;
import com.market.sellers.services.SellerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    @Autowired
    private SellersRepository sellersRepository;

    @Autowired
    private ProductsService productsService;

    @Autowired
    private MessageSource messageSource;

    private Logger logger = LoggerFactory.getLogger(this.getClass());

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
            throw new BaseHttpException(new ApiError(NOT_FOUND,
                    this.messageSource.getMessage("seller.not.found", null, Locale.getDefault())));
    }

    @Override
    public Seller findById(String sellerId) {
        this.logger.info("{}", sellerId);

        return this.sellersRepository.findById(sellerId).map(SellerDocument::convertToSeller)
                .orElseThrow(() -> new BaseHttpException(new ApiError(NOT_FOUND,
                        this.messageSource.getMessage("seller.not.found", null, Locale.getDefault()))));
    }

    @Override
    public List<Seller> findAll() {
        List<SellerDocument> sellers = this.sellersRepository.findAll();

        if(!sellers.isEmpty())
            return sellers.stream().map(SellerDocument::convertToSeller).collect(Collectors.toList());
        else
            throw new BaseHttpException(new ApiError(NOT_FOUND,
                    this.messageSource.getMessage("seller.not.found", null, Locale.getDefault())));
    }

    @Override
    public void delete(String sellerId) {
        this.productsService.deleteProducts(sellerId);
        this.sellersRepository.deleteById(sellerId);
    }
}
