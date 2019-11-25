package com.market.sellers.documents;

import com.market.sellers.model.Seller;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
        return SellerDocument.builder()
                .id(seller.getId())
                .name(seller.getName())
                .cnae(seller.getCnae())
                .build();
    }

    public Seller convertToSeller() {
        return new Seller()
                .id(this.getId())
                .name(this.getName())
                .cnae(this.getCnae());
    }
}
