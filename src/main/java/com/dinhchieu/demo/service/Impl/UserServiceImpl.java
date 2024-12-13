package com.dinhchieu.demo.service.Impl;

import com.dinhchieu.demo.dao.UserRepository;
import com.dinhchieu.demo.dto.request.UserRegisterRequestDTO;
import com.dinhchieu.demo.dto.request.UserUpdateInformRequestDTO;
import com.dinhchieu.demo.dto.response.PaginationResponseDTO;
import com.dinhchieu.demo.dto.response.UserInformationResponseDTO;
import com.dinhchieu.demo.entity.User;
import com.dinhchieu.demo.handle.AccountStateNotFoundException;
import com.dinhchieu.demo.handle.PasswordNotFoundException;
import com.dinhchieu.demo.handle.UserNotFoundException;
import com.dinhchieu.demo.service.UserService;
import com.dinhchieu.demo.utils.AccountState;
import com.dinhchieu.demo.utils.UserSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    private UserInformationResponseDTO mapToUserInformationResponseDTO(User user){
        UserInformationResponseDTO dto = new UserInformationResponseDTO();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        dto.setBalance(user.getBalance());
        dto.setActivation(user.getActivation());
        dto.setAccountState(String.valueOf(user.getAccountState()));
        dto.setRoleName(user.getRole().getRoleName());

        return dto;
    }

    @Override
    public UserInformationResponseDTO getUserById(int id) {
        User existingUser = userRepository.findById(id).orElseThrow( () -> new UserNotFoundException("User with id " + id + " is not found"));
        return mapToUserInformationResponseDTO(existingUser);
    }

    @Override
    public UserInformationResponseDTO getUserByUsername(String username) {
        Optional<User> user = Optional.ofNullable(userRepository.findUserByUsername(username));
        if(user.isEmpty()){
            throw new UserNotFoundException("User with username " + username + " is not found");
        }

        User existingUser = user.get();
        return mapToUserInformationResponseDTO(existingUser);
    }

    @Override
    public UserInformationResponseDTO getUserByEmail(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findUserByEmail(email));

        if(user.isEmpty()){
            throw new UserNotFoundException("User with email " + email + " is not found");
        }

        User existingUser = user.get();
        return mapToUserInformationResponseDTO(existingUser);
    }

    @Override
    public PaginationResponseDTO<UserInformationResponseDTO> getAllUsers(int pageNo, int pageSize, String sortBy, Map<String, String> filters) {
        // Build Specification for filter
        Specification<User> userSpecification = UserSpecification.UserSpecificationBuildFromFilter(filters);

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        Page<User> userPage = userRepository.findAll(userSpecification, pageable);

        List<UserInformationResponseDTO> userInformationResponseDTOS = userPage.stream().map(this::mapToUserInformationResponseDTO).toList();

        PaginationResponseDTO<UserInformationResponseDTO> response = new PaginationResponseDTO<>();
        response.setItems(userInformationResponseDTOS);
        response.setCurrentPage(userPage.getNumber());
        response.setTotalItems(userPage.getTotalElements());
        response.setTotalPages(userPage.getTotalPages());
        response.setPageSize(userPage.getSize());

        return response;
    }

    @Override
    public UserInformationResponseDTO registerUser(UserRegisterRequestDTO userRegisterRequestDTO) {
        return null;
    }

    @Override
    public UserInformationResponseDTO updateBasicInformationUser(int userId, UserUpdateInformRequestDTO userUpdateInformRequestDTO) {
         User existingUser = userRepository.findById(userId).orElseThrow( () -> new UserNotFoundException("User with id " + userId + " is not found"));

         if(userUpdateInformRequestDTO.getEmail() != null){
                existingUser.setEmail(userUpdateInformRequestDTO.getEmail());
         }

         if(userUpdateInformRequestDTO.getPhone() != null){
                existingUser.setPhone(userUpdateInformRequestDTO.getPhone());
         }

         if(userUpdateInformRequestDTO.getAddress() != null){
                existingUser.setAddress(userUpdateInformRequestDTO.getAddress());
         }

         userRepository.save(existingUser);

         return mapToUserInformationResponseDTO(existingUser);
    }

    @Override
    public UserInformationResponseDTO updateUserAccountState(int userId, AccountState accountState) {

        if(accountState == null ){
            throw new AccountStateNotFoundException("Account state can't be null");
        }

        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){
            throw new UserNotFoundException("User with id " + userId + " is not found");
        }


        User existingUser = user.get();

        existingUser.setAccountState(accountState);
        userRepository.save(existingUser);

        return mapToUserInformationResponseDTO(existingUser);
    }

    @Override
    public void updateUserPassword(int userId, String password) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){
            throw new UserNotFoundException("User with id " + userId + " is not found");
        }

        if(password == null || password.isEmpty()){
            throw new PasswordNotFoundException("Password can't be null or empty");
        }

//        if(password.equals())

        User existingUser = user.get();


    }

    @Override
    public void addBalance(int userId, float balance) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){
            throw new UserNotFoundException("User with id " + userId + " is not found");
        }

        User existingUser = user.get();

        existingUser.setBalance(existingUser.getBalance() + balance);
        userRepository.save(existingUser);
    }

    @Override
    public void subtractBalance(int userId, float balance) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){
            throw new UserNotFoundException("User with id " + userId + " is not found");
        }

        User existingUser = user.get();

        existingUser.setBalance(existingUser.getBalance() - balance);
        userRepository.save(existingUser);
    }

}
