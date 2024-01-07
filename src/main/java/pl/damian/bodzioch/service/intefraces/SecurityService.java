package pl.damian.bodzioch.service.intefraces;

import org.springframework.security.core.Authentication;

public interface SecurityService {

    String encryptMessage(String text);

    String decryptMessage(String encryptedText);

    String generateToken(Authentication authentication);
}
