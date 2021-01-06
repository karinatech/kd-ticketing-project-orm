package com.cybertek.repo;

import com.cybertek.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    User findByUserName(String userName);
    @Transactional
    void deleteByUserName(String userName);
    List<User>findAllByRoleDescriptionIgnoreCase(String role);



}
