package com.market.sellers.services.impl;

import com.market.sellers.documents.SellerDocument;
import com.market.sellers.exceptions.custom.BaseHttpException;
import com.market.sellers.integrations.products.services.ProductsService;
import com.market.sellers.model.Seller;
import com.market.sellers.repositories.SellersRepository;
import com.market.sellers.services.SellerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.market.sellers.TestDataBuilder.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class SellerServiceImplTest {
    private SellerService sellerService;

    @Mock
    private SellersRepository sellersRepository;

    @Mock
    private ProductsService productsService;

    @Mock
    private MessageSource messageSource;

    @Before
    public void setUp() {
        this.sellerService = new SellerServiceImpl();
        ReflectionTestUtils.setField(this.sellerService, "sellersRepository", this.sellersRepository);
        ReflectionTestUtils.setField(this.sellerService, "productsService", this.productsService);
        ReflectionTestUtils.setField(this.sellerService, "messageSource", this.messageSource);
    }

    @Test
    public void shouldSaveSellerAndReturnSellerObject() {
        when(this.sellersRepository.save(any(SellerDocument.class))).thenReturn(buildSellerDocument());
        Seller seller = this.sellerService.save(buildSeller());

        assertNotNull(seller);
    }

    @Test(expected = NullPointerException.class)
    public void shouldThrowNullPointerExceptionWhenNoSellerProvided() {
        when(this.sellersRepository.save(any(SellerDocument.class))).thenReturn(buildSellerDocument());
        this.sellerService.save(null);
    }

    @Test
    public void shouldEditSellerAndReturnSellerObject() {
        when(this.sellersRepository.findById(anyString())).thenReturn(Optional.of(buildSellerDocument()));
        when(this.sellersRepository.save(any(SellerDocument.class))).thenReturn(buildSellerDocument());

        Seller seller = this.sellerService.edit(buildSeller());
        assertNotNull(seller);
    }

    @Test(expected = BaseHttpException.class)
    public void shouldThrowBaseHttpExceptionWhenEditAndNoSellerFound() {
        when(this.sellersRepository.findById(anyString())).thenReturn(Optional.empty());
        this.sellerService.edit(buildSeller());
    }

    @Test
    public void shouldFindByIdAndReturnSeller() {
        when(this.sellersRepository.findById(anyString())).thenReturn(Optional.of(buildSellerDocument()));
        assertNotNull(this.sellerService.findById("test_seller_id"));
    }

    @Test(expected = BaseHttpException.class)
    public void shouldFindNoSellerAndThrowBaseHttpException() {
        when(this.messageSource.getMessage(anyString(), any(), any())).thenReturn("Seller not found");
        when(this.sellersRepository.findById(anyString())).thenReturn(Optional.empty());
        this.sellerService.findById("test_seller_id");
    }

    @Test
    public void shouldFindAllSuccessfully() {
        when(this.sellersRepository.findAll()).thenReturn(buildSellerDocumentsList());
        List<Seller> sellers = this.sellerService.findAll();

        assertNotNull(sellers);
    }

    @Test(expected = BaseHttpException.class)
    public void shouldTryToFindAllAndThrowBaseHttpException() {
        when(this.sellersRepository.findAll()).thenReturn(new ArrayList<>());
        this.sellerService.findAll();
    }

    @Test
    public void deleteSellerShouldCallProductsServiceDeleteProducts() {
        doNothing().when(this.productsService).deleteProducts(anyString());
        doNothing().when(this.sellersRepository).deleteById(anyString());

        this.sellerService.delete("test_seller_id");

        verify(this.productsService, times(1)).deleteProducts(anyString());
    }

    @Test
    public void deleteSellerShouldCallSellerRepositoryDeleteById() {
        doNothing().when(this.productsService).deleteProducts(anyString());
        doNothing().when(this.sellersRepository).deleteById(anyString());

        this.sellerService.delete("test_seller_id");

        verify(this.sellersRepository, times(1)).deleteById(anyString());
    }
}