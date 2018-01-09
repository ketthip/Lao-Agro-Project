package security;

import domain.User;
import domain.UserRepository;
import domain.UserRolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;
    private final UserRolesRepository userRolesRepository;

//       Constructors
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository1, UserRolesRepository userRolesRepository1){
        this.userRepository = userRepository1;
        this.userRolesRepository = userRolesRepository1;

    }

//Method to securely compare username in login time
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByUserName(username);
        if (user == null){
            throw new UsernameNotFoundException("No user present with username: " + username);

        }else {

            List<String> userRoles = userRolesRepository.findRoleByUserName(username);
            return new CustomUserDetails(user, userRoles);
        }
    }
}
