package ru.astondevs.mapper;

import org.junit.Test;
import ru.astondevs.dto.UserDto;
import ru.astondevs.entity.UserEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {

    @Test
    public void toEntity() {
        UserEntity entity = new UserEntity();
        entity.setId(1);
        entity.setName("test");
        entity.setEmail("test@test.com");
        entity.setAge(25);
        entity.setCreatedAt(LocalDateTime.now());

        UserDto dto = UserMapper.toDto(entity);

        assertNotNull(dto);;
        assertAll(
                () -> assertEquals(entity.getId(), dto.getId()),
                () -> assertEquals(entity.getName(), dto.getName()),
                () -> assertEquals(entity.getEmail(), dto.getEmail()),
                () -> assertEquals(entity.getAge(), dto.getAge()),
                () -> assertEquals(entity.getCreatedAt(), dto.getCreatedAt())
        );
    }
    @Test
    public void toDto() {
        LocalDateTime now = LocalDateTime.now();
        UserDto dto = new UserDto(5, "Test", "test@test.ru", 32, now);

        UserEntity entity = UserMapper.toEntity(dto);

        assertNotNull(entity);
        assertAll(
                () -> assertEquals(dto.getId(), entity.getId()),
                () -> assertEquals(dto.getName(), entity.getName()),
                () -> assertEquals(dto.getEmail(), entity.getEmail()),
                () -> assertEquals(dto.getCreatedAt(), entity.getCreatedAt())
        );
    }
}