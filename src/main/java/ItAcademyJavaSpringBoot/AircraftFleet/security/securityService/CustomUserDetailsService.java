package ItAcademyJavaSpringBoot.AircraftFleet.security.securityService;

import ItAcademyJavaSpringBoot.AircraftFleet.model.sql.User;
import ItAcademyJavaSpringBoot.AircraftFleet.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Objects;


@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    private User userDetail;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername({})", username);
        userDetail = userRepository.findByUserName(username);

        if(!Objects.isNull(userDetail)){
            return new org.springframework
                    .security
                    .core
                    .userdetails
                    .User(userDetail.getUserName(),
                    userDetail.getPassword(),
                    new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found Exception");
        }
    }

//    public User getUserDetail(){
//
//    }
}
