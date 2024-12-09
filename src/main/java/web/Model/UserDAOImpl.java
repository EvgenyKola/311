package web.Model;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.util.List;


@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    private EntityManager entityManager;

  
    public List<User> getAllUsers() {
        return entityManager.createQuery("SELECT user FROM User user", User.class).getResultList();
    }

    public Integer deleteUser(Long userId) {
        return entityManager.createQuery("DELETE FROM User user WHERE user.id = :userId")
                .setParameter("userId", userId)
                .executeUpdate();
    }

    public void createUser(User user) {
        entityManager.persist(user);
    };

    public void editUser(User user) {
        entityManager.merge(user);
    };

    public User getUser(Long userId) {
        return entityManager.find(User.class, userId);
    };



}
