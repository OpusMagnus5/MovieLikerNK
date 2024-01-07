package pl.damian.bodzioch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import pl.damian.bodzioch.exception.AppException;
import pl.damian.bodzioch.service.intefraces.SecurityService;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Service
@Slf4j
public class SecurityServiceBean implements SecurityService {

    private final JwtEncoder jwtEncoder;
    private final SecretKey secretKey;

    public SecurityServiceBean(JwtEncoder jwtEncoder) throws NoSuchAlgorithmException {
        this.jwtEncoder = jwtEncoder;
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(256);
        this.secretKey = keyGenerator.generateKey();
    }

    @Override
    public String encryptMessage(String text) {
        try {
            byte[] iv = generateIv();
            GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmSpec);
            byte[] encryptedBytes = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));
            byte[] outputBytes = attachIvAndGetOutput(iv, encryptedBytes);
            return Base64.getUrlEncoder().encodeToString(outputBytes);
        } catch (GeneralSecurityException e) {
            log.error("Cipher exception during encrypt message: {}", text, e);
            throw AppException.getGeneralException(e);
        }
    }

    @Override
    public String decryptMessage(String encryptedText) {
        try {
            byte[] encryptedBytes = Base64.getUrlDecoder().decode(encryptedText);
            byte[] iv = extractIv(encryptedBytes);
            byte[] ciphertext = extractEncodedTextBytes(encryptedBytes, iv);
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);
            byte[] decryptedBytes = cipher.doFinal(ciphertext);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (GeneralSecurityException e) {
            log.error("Cipher exception during decrypt message: {}", encryptedText, e);
            throw AppException.getGeneralException(e);
        }
    }

    @Override
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(authentication.getName())
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.HOURS))
                .build();
        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS512).build();
        JwtEncoderParameters encoderParameters = JwtEncoderParameters.from(jwsHeader, claims);
        return this.jwtEncoder.encode(encoderParameters).getTokenValue();
    }

    private byte[] attachIvAndGetOutput(byte[] iv, byte[] encryptedBytes) {
        byte[] outputBytes = new byte[iv.length + encryptedBytes.length];
        System.arraycopy(iv, 0, outputBytes, 0, iv.length);
        System.arraycopy(encryptedBytes, 0, outputBytes, iv.length, encryptedBytes.length);
        return outputBytes;
    }

    private byte[] generateIv() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[16];
        secureRandom.nextBytes(iv);
        return iv;
    }

    private byte[] extractIv(byte[] encryptedBytes) {
        byte[] iv = new byte[16];
        System.arraycopy(encryptedBytes, 0, iv, 0, iv.length);
        return iv;
    }

    private byte[] extractEncodedTextBytes(byte[] encryptedBytes, byte[] iv) {
        byte[] ciphertext = new byte[encryptedBytes.length - iv.length];
        System.arraycopy(encryptedBytes, iv.length, ciphertext, 0, ciphertext.length);
        return ciphertext;
    }
}
