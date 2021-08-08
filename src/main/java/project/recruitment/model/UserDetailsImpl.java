package project.recruitment.model;

import lombok.Builder;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

@Value
@Builder
public class UserDetailsImpl implements UserDetails
{
    Long id;
    String username;
    String password;
    boolean accountNonExpired;
    boolean accountNonLocked;
    boolean credentialsNonExpired;
    boolean enabled;
    List<GrantedAuthority> authorities;
}
