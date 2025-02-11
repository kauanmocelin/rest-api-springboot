package dev.kauanmocelin.springbootrestapi.appuser;

import dev.kauanmocelin.springbootrestapi.registration.token.ConfirmationToken;
import dev.kauanmocelin.springbootrestapi.registration.token.ConfirmationTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {

    private final AppUserRepository appUserRepository;
    private final ConfirmationTokenService confirmationTokenService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException(String.format("user with email %s not found", email)));
    }

    public String signUpUser(AppUser appUser){
        boolean userExists = appUserRepository.findByEmail(appUser.getEmail())
            .isPresent();
        if(userExists){
            // TODO check of attributes are the same and
            // TODO if email not confirmed send confirmation email.
            throw new IllegalStateException("email already taken");
        }
        String encodedPassword = passwordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);

        appUserRepository.save(appUser);

        final var token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
            token,
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            appUser
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }
}
