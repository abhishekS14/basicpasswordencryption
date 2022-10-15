package com.basicpassword.basicpasswordencryption.Service;

import com.basicpassword.basicpasswordencryption.Model.User;
import com.basicpassword.basicpasswordencryption.Repository.UserRepository;
import com.basicpassword.basicpasswordencryption.exception.UsernameNotAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EncryptPasswordService {

    @Autowired
    UserRepository userRepository;

    public EncryptPasswordService() {
        //empty constructor
    }

    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public void encodePassword(RequestEntity<User> user) {
        User userDetails = user.getBody();
        boolean isValid = userExists(user);
        boolean signUpStatus = false;

        if (!isValid) {
            PasswordEncoder passwordEncoder = getPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(userDetails.getPassword());
            userDetails.setPassword(encodedPassword);

            userRepository.save(userDetails);
            signUpStatus = true;
        }
        
        if(!signUpStatus) {
            throw new UsernameNotAvailableException();
        }

    }

    public boolean isAuthenticated(RequestEntity<User> user) {
        User userDetails = user.getBody();
        boolean isValid = userExists(user);
        boolean loginStatus = false;


        if (isValid) {
            PasswordEncoder passwordEncoder = getPasswordEncoder();
            Optional<User> dbUser = userRepository.findById(userDetails.getId());
            String dbPassword = dbUser.get().getPassword();
            return passwordEncoder.matches(userDetails.getPassword(), dbPassword);
        }

        if(!loginStatus) {
            throw new BadCredentialsException("Username/Password is incorrect");
        }

        return false;

    }

    public boolean userExists(RequestEntity<User> user) {
        User userDetails = user.getBody();
        List<User> allUsers = userRepository.findAll();

        for (User u: allUsers) {
            if ((u.getUserName()).equalsIgnoreCase(userDetails.getUserName())) {
                return true;
            }
        }

        return false;
    }
}
