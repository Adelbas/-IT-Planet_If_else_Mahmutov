package ru.adel.if_else_task_2.core.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.adel.if_else_task_2.core.service.db.ClientDbService;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClientDbService clientDbService;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        return new CustomUserDetails(
                clientDbService.findClientById(Long.parseLong(id))
                        .orElseThrow(
                                ()->new UsernameNotFoundException("User not found")
                        )
        );
    }
}
