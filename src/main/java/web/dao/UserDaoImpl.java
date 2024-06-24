package web.dao;


import org.springframework.stereotype.Repository;
import web.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<User> getUserById(long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> showAllUsers() {
        return entityManager.createQuery("select p from User p", User.class).getResultList();
    }

    @Override
    public void createNewUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public void removeUserById(long id) {
        User user = entityManager.find(User.class, id);
        entityManager.remove(user);
    }
}