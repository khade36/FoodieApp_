package com.user.user.repositary;

import com.user.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositary extends JpaRepository <User,String> {
   User findByEmailIdAndPassword(String emailId,String password);


}
