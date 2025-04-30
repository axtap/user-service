package ru.astondevs.dao;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.astondevs.dto.UserDto;
import ru.astondevs.util.HibernateUtil;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDaoTest {

    @Container
    private final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("test_db")
            .withUsername("test_user")
            .withPassword("test_pass");

    private UserDao userDao;

    @BeforeAll
    void setUp() throws IOException {
        container.start();

        // Перезаписываем hibernate.properties
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        properties.setProperty("hibernate.connection.url", container.getJdbcUrl());
        properties.setProperty("hibernate.connection.username", container.getUsername());
        properties.setProperty("hibernate.connection.password", container.getPassword());
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop"); // Для тестов: создаёт и удаляет таблицы
        properties.setProperty("hibernate.show_sql", "true");

        try (FileOutputStream out = new FileOutputStream("src/test/resources/hibernate.properties")) {
            properties.store(out, null);
        }

        userDao = new UserDao();
    }

    @AfterAll
    void tearDown() {
        HibernateUtil.shutdown();
        container.stop();
    }

    @Test
    public void create() {
        UserDto userDto = new UserDto();
        userDto.setName("test_user");
        userDto.setEmail("test@test.com");
        userDto.setAge(32);
        userDto.setCreatedAt(LocalDateTime.now());

        userDao.create(userDto);
    }

    @Test
    public void read() {
        UserDto foundUser = userDao.read(1);
        assertNotNull(foundUser);
        assertEquals("test_user", foundUser.getName());
        assertEquals("test@test.com", foundUser.getEmail());
        assertEquals(32, foundUser.getAge());
    }

    @Test
    public void update() {
        UserDto userDto = userDao.read(1);
        assertNotNull(userDto);

        userDto.setName("test_user2");
        userDto.setEmail("test2@test.com");
        userDto.setAge(48);

        userDao.update(userDto);

        UserDto updatedUser = userDao.read(1);
        assertNotNull(updatedUser);
        assertEquals("test_user2", updatedUser.getName());
        assertEquals("test2@test.com", updatedUser.getEmail());
        assertEquals(48, updatedUser.getAge());
    }

    @Test
    public void getAll() {
        UserDto newUser = new UserDto();
        newUser.setName("test_user3");
        newUser.setEmail("test3@test.com");
        newUser.setAge(25);
        newUser.setCreatedAt(LocalDateTime.now());
        userDao.create(newUser);

        List<UserDto> users = userDao.getAll();
        assertFalse(users.isEmpty());
        assertEquals(2, users.size());
    }

    @Test
    public void delete() {
        userDao.delete(1);
        assertNull(userDao.read(1));
    }
}
