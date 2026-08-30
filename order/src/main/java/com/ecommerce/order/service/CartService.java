package com.ecommerce.order.service;

import com.ecommerce.order.dto.CartItemRequest;
import com.ecommerce.order.model.CartItem;

import com.ecommerce.order.repository.CartItemRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final CartItemRepository cartItemRepository;


    public boolean addToCart(String userId, CartItemRequest request) {
        if (userId != null) userId = userId.trim();
        String productId = request.getProductId() != null ? request.getProductId().trim() : null;

        //find item for user and product, check if it already has that product
        CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
        if(existingCartItem != null) {
            //update qtn
            existingCartItem.setQuantity(existingCartItem.getQuantity() + request.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(existingCartItem);
        }else{
            //create new cart item
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(productId);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(cartItem);

        }
        return true;


    }

    public boolean deleteItemFromCart(String userId, String productId) {
        if (userId != null) userId = userId.trim();
        if (productId != null) productId = productId.trim();
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
        if(cartItem != null){
            cartItemRepository.delete(cartItem);
            return true;
        }
        return false;
    }

    public List<CartItem> getCart(String userId) {
        if (userId != null) userId = userId.trim();
        return cartItemRepository.findByUserId(userId);
    }

    public void clearCart(String userId) {
        if (userId != null) userId = userId.trim();
        cartItemRepository.deleteByUserId(userId);
    }
}
