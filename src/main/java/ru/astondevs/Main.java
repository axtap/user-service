package ru.astondevs;

import ru.astondevs.dao.UserDao;
import ru.astondevs.dto.UserDto;
import ru.astondevs.mapper.UserMapper;
import ru.astondevs.service.UserService;
import ru.astondevs.util.HibernateUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (HibernateUtil hibernateUtil = new HibernateUtil()) {
            new UserService(
                    System.in,
                    new UserDao(hibernateUtil.getSession(), new UserMapper())
            )
                    .manage();
        }
    }
}