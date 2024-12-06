package web.user;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;


@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT user FROM User user", User.class).getResultList();
    }

    @Transactional
    public Integer deleteUser(Long userId) {
        return entityManager.createQuery("DELETE FROM User user WHERE user.id = :userId")
                .setParameter("userId", userId)
                .executeUpdate();
    }

    @Transactional
    public void createUser(User user) {
        entityManager.persist(user);
    };

    @Transactional
    public void editUser(User user) {
        entityManager.merge(user);
    };

    @Transactional
    public User getUser(Long userId) {
        return entityManager.find(User.class, userId);
    };



}
