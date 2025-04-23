package ru.astondevs.mapper;

import ru.astondevs.dto.UserDto;
import ru.astondevs.entity.UserEntity;

public class UserMapper {

    // Преобразование UserEntity -> UserDto
    public static UserDto toDto(UserEntity entity) {
        return new UserDto(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getAge(),
                entity.getCreatedAt()
        );
    }

    // Преобразование UserDto -> UserEntity
    public static UserEntity toEntity(UserDto dto) {
        UserEntity entity = new UserEntity();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setEmail(dto.getEmail());
        entity.setAge(dto.getAge());
        entity.setCreatedAt(dto.getCreatedAt());
        return entity;
    }
}