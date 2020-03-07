package com.market.sellers;

import com.market.sellers.documents.SellerDocument;
import com.market.sellers.model.Seller;

import java.util.Arrays;
import java.util.List;

public abstract class TestDataBuilder {
    public static Seller buildSeller() {
        return new Seller()
                .id("seller_test_id")
                .name("seller_test_name")
                .cnae(15);
    }

    public static List<Seller> buildSellersList() {
        return Arrays.asList(buildSeller(), buildSeller(), buildSeller());
    }

    public static List<SellerDocument> buildSellerDocumentsList() {
        return Arrays.asList(buildSellerDocument(), buildSellerDocument(), buildSellerDocument());
    }

    public static SellerDocument buildSellerDocument() {
        return SellerDocument.build(buildSeller());
    }
}
