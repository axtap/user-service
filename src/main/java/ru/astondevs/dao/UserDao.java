package ru.astondevs.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.astondevs.dto.UserDto;
import ru.astondevs.entity.UserEntity;
import ru.astondevs.mapper.UserMapper;
import ru.astondevs.util.HibernateUtil;

import java.util.List;
import java.util.stream.Collectors;

public class UserDao {
    private final Session session;
    private final UserMapper mapper;

    public UserDao(Session session, UserMapper mapper) {
        this.session = session;
        this.mapper = mapper;
    }

    // Создание
    public void create(UserDto userDto) {
        UserEntity entity = mapper.toEntity(userDto);
        //Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            //session.close();
        }
    }

    // Чтение по ID
    public UserDto read(int id) {
        //Session session = HibernateUtil.getSessionFactory().openSession();
        UserEntity entity = session.get(UserEntity.class, id);
        //session.close();
        return entity != null ? mapper.toDto(entity) : null;
    }

    // Обновление
    public void update(UserDto userDto) {
        UserEntity entity = mapper.toEntity(userDto);
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            //session.close();
        }
    }

    // Удаление
    public void delete(int id) {
        //Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();

        try {

            UserEntity entity = session.get(UserEntity.class, id);
            if (entity != null) {
                session.delete(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            //session.close();
        }
    }

    // Получение всех
    public List<UserDto> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<UserEntity> entities = session.createQuery("FROM UserEntity", UserEntity.class).list();
        session.close();
        return entities.stream()
                .map(UserMapper::toDto)
                .collect(Collectors.toList());
    }
}