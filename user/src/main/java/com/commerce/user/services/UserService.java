package com.commerce.user.services;

import com.commerce.user.dto.UserRequest;
import com.commerce.user.dto.UserResponse;
import com.commerce.user.dto.AddressDTO;
import com.commerce.user.models.Address;
import com.commerce.user.models.User;
import com.commerce.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class UserService {
    private final UserRepository userRepository;
    private Long nextId =1L;

    public List<UserResponse> fetchAllUser(){
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());

    }

    public void addUser( UserRequest userRequest ){
                 User user= new User();
                 updateUserFromRequest( user, userRequest);
                 userRepository.save(user );
    }

    public Optional<UserResponse> fetchAUser(String id) {
        return userRepository.findById(id)
                .map(this::mapToUserResponse);
    }
    public boolean updateUser(String id, UserRequest updatedUser){
        return userRepository.findById(id)
                .map(existingUser-> {
                    updateUserFromRequest(existingUser, updatedUser);
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }



    private void updateUserFromRequest( User user,UserRequest userRequest) {
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPhone(userRequest.getPhone());
        if(userRequest.getAddress() != null){
            Address address = new Address();
            address.setCity(userRequest.getAddress().getCity());
            address.setState(userRequest.getAddress().getState());
            address.setCountry(userRequest.getAddress().getCountry());
            address.setStreet(userRequest.getAddress().getStreet());
            address.setZipcode(userRequest.getAddress().getZipcode());
            user.setAddress(address);
        }

    }

    private UserResponse  mapToUserResponse(User user){
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setPhone(user.getPhone());
        response.setRole(user.getRole());

        if( user.getAddress() != null){
            AddressDTO addressDTO =new AddressDTO();
            addressDTO.setStreet(user.getAddress().getStreet());
            addressDTO.setCity(user.getAddress().getCity());
            addressDTO.setState(user.getAddress().getState());
            addressDTO.setCountry(user.getAddress().getCountry());
            addressDTO.setZipcode(user.getAddress().getZipcode());
            response.setAddress(addressDTO);

        }
        return response;

    }
}



