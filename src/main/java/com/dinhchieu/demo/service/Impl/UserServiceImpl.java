package com.dinhchieu.demo.service.Impl;

import com.dinhchieu.demo.dao.RoleRepository;
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
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    private void validateUserRegisterRequest(UserRegisterRequestDTO dto) {
        if (dto.getUsername() == null || dto.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username can't be null or empty");
        }
        if (dto.getEmail() == null || dto.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email can't be null or empty");
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password can't be null or empty");
        }
        if (dto.getPhone() == null || dto.getPhone().isEmpty()) {
            throw new IllegalArgumentException("Phone can't be null or empty");
        }
        if (dto.getAddress() == null || dto.getAddress().isEmpty()) {
            throw new IllegalArgumentException("Address can't be null or empty");
        }
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
        validateUserRegisterRequest(userRegisterRequestDTO);

        if (userRepository.findUserByUsername(userRegisterRequestDTO.getUsername()) != null) {
            throw new UserNotFoundException("Username " + userRegisterRequestDTO.getUsername() + " is already existed!");
        }

        if (userRepository.findUserByEmail(userRegisterRequestDTO.getEmail()) != null) {
            throw new UserNotFoundException("Email " + userRegisterRequestDTO.getEmail() + " is already existed!");
        }

        if (userRepository.findUserByPhone(userRegisterRequestDTO.getPhone()) != null) {
            throw new UserNotFoundException("Phone " + userRegisterRequestDTO.getPhone() + " is already existed!");
        }

        User user = new User();
        user.setUsername(userRegisterRequestDTO.getUsername());
        user.setEmail(userRegisterRequestDTO.getEmail());
        user.setPhone(userRegisterRequestDTO.getPhone());
        user.setAddress(userRegisterRequestDTO.getAddress());
        user.setPassword(passwordEncoder.encode(userRegisterRequestDTO.getPassword()));
        user.setRole(roleRepository.findRoleByRoleName("USER"));
        user.setAccountState(AccountState.PENDING);

        userRepository.save(user);

        return mapToUserInformationResponseDTO(user);
    }

    @Override
    public UserInformationResponseDTO updateBasicInformationUser(int userId, UserUpdateInformRequestDTO userUpdateInformRequestDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User with id " + userId + " is not found"));

        if (userUpdateInformRequestDTO.getEmail() != null && !userUpdateInformRequestDTO.getEmail().isEmpty()) {

            if(userRepository.findUserByEmail(userUpdateInformRequestDTO.getEmail()) != null){
                throw new UserNotFoundException("Email " + userUpdateInformRequestDTO.getEmail() + " is already existed!");
            }

            existingUser.setEmail(userUpdateInformRequestDTO.getEmail());
        }

        if (userUpdateInformRequestDTO.getPhone() != null && !userUpdateInformRequestDTO.getPhone().isEmpty()) {

            if(userRepository.findUserByPhone(userUpdateInformRequestDTO.getPhone()) != null){
                throw new UserNotFoundException("Phone " + userUpdateInformRequestDTO.getPhone() + " is already existed!");
            }

            existingUser.setPhone(userUpdateInformRequestDTO.getPhone());
        }

        if (userUpdateInformRequestDTO.getAddress() != null && !userUpdateInformRequestDTO.getAddress().isEmpty()) {
            existingUser.setAddress(userUpdateInformRequestDTO.getAddress());
        }

        userRepository.save(existingUser);

        return mapToUserInformationResponseDTO(existingUser);
    }


    @Override
    public UserInformationResponseDTO updateUserAccountState(int userId, String accountState) {

        try{
           AccountState.valueOf(accountState);
        }catch (IllegalArgumentException e){
            throw new AccountStateNotFoundException("Account state " + accountState + " is not found");
        }

        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){
            throw new UserNotFoundException("User with id " + userId + " is not found");
        }

        User existingUser = user.get();

        existingUser.setAccountState(AccountState.valueOf(accountState));
        userRepository.save(existingUser);

        return mapToUserInformationResponseDTO(existingUser);
    }

    @Override
    public void updateUserPassword(int userId, String oldPassword, String newPassword) {
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){
            throw new UserNotFoundException("User with id " + userId + " is not found");
        }

        if((oldPassword == null || oldPassword.isEmpty()  )|| (newPassword == null || newPassword.isEmpty() )){
            throw new PasswordNotFoundException("These passwords can't be null or empty");
        }

        if(!BCrypt.checkpw(oldPassword, user.get().getPassword())){
            throw new PasswordNotFoundException("Please enter the correct old password");
        }

        User existingUser = user.get();

        existingUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(existingUser);
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
