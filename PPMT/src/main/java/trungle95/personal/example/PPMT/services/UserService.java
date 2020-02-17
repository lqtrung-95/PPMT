package trungle95.personal.example.PPMT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import trungle95.personal.example.PPMT.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


}
