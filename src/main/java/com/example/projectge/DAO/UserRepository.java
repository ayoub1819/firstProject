package com.example.projectge.DAO;
import com.example.projectge.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

     User findUserByUsername(String username);
     User findUserByEmail(String email);

}
