package ma.enset.tp2_jwt_demo.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ma.enset.tp2_jwt_demo.entity.AppUser;
import ma.enset.tp2_jwt_demo.repository.UserRepository;

@Service 
public class CustomerUserDetailsService implements UserDetailsService{
    private final UserRepository userRepository;

    public CustomerUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser= userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("Email untrouvable"));

        return User.builder()
                .username(appUser.getEmail())
                .password(appUser.getPassword())
                .roles(appUser.getRole())
                .build();
    }
    

}
