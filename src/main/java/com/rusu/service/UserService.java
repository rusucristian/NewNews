package com.rusu.service;

import com.rusu.domain.User;
import com.rusu.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByUsername(s);
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User getOne(Long id){
        return userRepository.getOne(id);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }

    public void saveUser(Long id, User user){
        User currentUser = getOne(id);

        if (user.getPassword()!=null && user.getPassword().isEmpty()) {
            user.setPassword(currentUser.getPassword());
        }

        userRepository.save(user);
    }

}
