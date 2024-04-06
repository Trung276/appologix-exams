package com.apollogix.exams.service.impl;

import com.apollogix.exams.dto.request.user.UserCreationRequest;
import com.apollogix.exams.dto.request.user.UserUpdateEmailRequest;
import com.apollogix.exams.dto.request.user.UserUpdateRequest;
import com.apollogix.exams.dto.response.user.UserResponse;
import com.apollogix.exams.entity.User;
import com.apollogix.exams.enums.Role;
import com.apollogix.exams.exception.AppException;
import com.apollogix.exams.exception.ErrorCode;
import com.apollogix.exams.mapper.UserMapper;
import com.apollogix.exams.repository.UserRepository;
import com.apollogix.exams.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.default.account}")
    private String DEFAULT_ACCOUNT;

    @Override
    public UserResponse getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            String email = authentication.getName();

            User user = userRepository.findByEmailAndIsActivatedTrue(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

            return userMapper.toUserResponse(user);
        } else {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    @Override
    public UserResponse create(UserCreationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User newUser = userMapper.toUser(request);
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser = userRepository.save(newUser);

        return userMapper.toUserResponse(newUser);
    }

    @Override
    public UserResponse getById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toUserResponse(user);
    }


    @Override
    public UserResponse updateById(String userId, UserUpdateRequest request) {
        User userToUpdate = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (!isValidUpdateRequest(userToUpdate, request)) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }

        String oldPassword = userToUpdate.getPassword();

        userMapper.update(userToUpdate, request);

        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            userToUpdate.setPassword(passwordEncoder.encode(request.getPassword()));
        } else {
            userToUpdate.setPassword(oldPassword);
        }

        userToUpdate = userRepository.save(userToUpdate);

        return userMapper.toUserResponse(userToUpdate);
    }

    @Override
    public UserResponse updateEmail(UserUpdateEmailRequest request) {
        if (!isValidUpdateEmailRequest(request)) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        User userToUpdate = userRepository.findByEmail(request.getOldEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        userToUpdate.setEmail(request.getNewEmail());
        String oldPassword = userToUpdate.getPassword();
        userToUpdate.setPassword(oldPassword);

        userToUpdate = userRepository.save(userToUpdate);

        return userMapper.toUserResponse(userToUpdate);
    }


    @Override
    public void deleteById(String userId) {
        User userToDelete = userRepository.findById(userId)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        if (isDefaultAccount(userToDelete.getEmail())) {
            throw new AppException(ErrorCode.INVALID_DEFAULT_ACCOUNT);
        }

        userToDelete.setIsActivated(false);

        userRepository.save(userToDelete);
    }


    @Override
    public List<UserResponse> getAll() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUsersByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        return userMapper.toUserResponse(user);
    }

    @Override
    public List<UserResponse> getUsersByKeyword(String keyword) {
        List<User> users = userRepository.findByFullNameContainingIgnoreCase(keyword);

        return users.stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserResponse> getUsersByRole(String role) {
        List<User> users = userRepository.findByRole(Role.valueOf(role.toUpperCase()));

        return users.stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    private boolean isValidUpdateRequest(User userToUpdate, UserUpdateRequest request) {
        UserResponse currentUser = getCurrentUser();

        if (isDefaultAccount(currentUser.getEmail())) {
            if (isDefaultAccount(userToUpdate.getEmail())) {
                if(!request.getRole().equals(Role.TEACHER.name()) || !request.getIsActivated()) {
                    throw new AppException(ErrorCode.INVALID_DEFAULT_ACCOUNT);
                }
            }
        } else {
            if (isDefaultAccount(userToUpdate.getEmail())) {
                return false;
            } else {
                if (isTeacher(userToUpdate.getRole().name())) {
                    return request.getRole().equals(Role.TEACHER.name());
                }
            }
        }
        return true;
    }

    private boolean isDefaultAccount(String email) {
        return email.equals(DEFAULT_ACCOUNT);
    }

    public boolean isTeacher(String role) {
        return role.equals(Role.TEACHER.name());
    }

    private boolean isValidUpdateEmailRequest(UserUpdateEmailRequest request) {
        if (!getCurrentUser().getEmail().equals(DEFAULT_ACCOUNT) || request.getOldEmail().equals(DEFAULT_ACCOUNT)) {
            throw new AppException(ErrorCode.ACCESS_DENIED);
        }

        return userRepository.existsByEmail(request.getOldEmail()) && !userRepository.existsByEmail(request.getNewEmail());
    }
}
