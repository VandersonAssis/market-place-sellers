package com.market.sellers.repositories;

import com.market.sellers.documents.SellerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellersRepository extends MongoRepository<SellerDocument, String> {}
