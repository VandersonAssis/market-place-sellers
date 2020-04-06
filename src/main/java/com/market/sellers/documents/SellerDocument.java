package com.market.sellers.documents;

import com.market.sellers.model.Seller;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "sellers")
public class SellerDocument {
    @Id
    private String id;

    @Indexed(unique = true)
    private String name;
    private int cnae;

    @CreatedDate
    private LocalDateTime dateCreated;

    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public static SellerDocument build(Seller seller) {
        return new SellerDocument()
                .setId(seller.getId())
                .setName(seller.getName())
                .setCnae(seller.getCnae());
    }

    public Seller convertToSeller() {
        return new Seller()
                .id(this.getId())
                .name(this.getName())
                .cnae(this.getCnae());
    }

    public String getId() {
        return id;
    }

    public SellerDocument setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public SellerDocument setName(String name) {
        this.name = name;
        return this;
    }

    public int getCnae() {
        return cnae;
    }

    public SellerDocument setCnae(int cnae) {
        this.cnae = cnae;
        return this;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public SellerDocument setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public SellerDocument setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
        return this;
    }
}
