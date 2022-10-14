package com.basicpassword.basicpasswordencryption.Service;

import com.basicpassword.basicpasswordencryption.Model.User;
import com.basicpassword.basicpasswordencryption.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EncryptPasswordService {

    @Autowired
    UserRepository userRepository;

    public EncryptPasswordService() {
    }

    public void encodePassword(RequestEntity<User> user) {
        User userDetails = user.getBody();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(userDetails.getPassword());
        userDetails.setPassword(encodedPassword);

        userRepository.save(userDetails);
    }

    public boolean isAuthenticated(RequestEntity<User> user) {
        User userDetails = user.getBody();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Optional<User> dbUser = userRepository.findById(userDetails.getId());
        String dbPassword = dbUser.get().getPassword();
        return passwordEncoder.matches(userDetails.getPassword(), dbPassword);

    }
}
