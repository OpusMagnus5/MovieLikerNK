package pl.damian.bodzioch.service.intefraces;

public interface SecurityService {

    String encryptMessage(String text);

    String decryptMessage(String encryptedText);
}
