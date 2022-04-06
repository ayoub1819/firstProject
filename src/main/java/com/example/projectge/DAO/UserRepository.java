package com.example.projectge.DAO;
import com.example.projectge.models.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long>{

    @Query(value = "select u from User u where u.username = ?1")
      User findByUsername(String username);

}
