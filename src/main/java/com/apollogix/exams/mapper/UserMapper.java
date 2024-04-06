package com.apollogix.exams.mapper;

import com.apollogix.exams.dto.request.user.UserCreationRequest;
import com.apollogix.exams.dto.request.user.UserUpdateEmailRequest;
import com.apollogix.exams.dto.request.user.UserUpdateRequest;
import com.apollogix.exams.dto.response.user.UserResponse;
import com.apollogix.exams.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    User update(@MappingTarget User user, UserUpdateRequest request);

    User update(@MappingTarget User user, UserUpdateEmailRequest request);
}
