package com.ecommerce.order.service;

import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.model.CartItem;
import com.ecommerce.order.repository.CartItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemRepository cartItemRepository;

    @BeforeEach
    void setUp() {
        cartItemRepository.deleteAll();
    }

    @Test
    void testAddToCartAndGetCart() {
        String userId = "67d533476a55cf5c8124a59c";
        CartItemRequest request = new CartItemRequest();
        request.setProductId("1");
        request.setQuantity(2);

        // Add to cart
        boolean result = cartService.addToCart(userId, request);
        assertTrue(result);

        // Get cart items for the user
        List<CartItem> cartItems = cartService.getCart(userId);
        assertNotNull(cartItems);
        assertEquals(1, cartItems.size());
        assertEquals(userId, cartItems.get(0).getUserId());
        assertEquals("1", cartItems.get(0).getProductId());
        assertEquals(2, cartItems.get(0).getQuantity());
    }
}
