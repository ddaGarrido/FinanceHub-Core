package com.acme.financehub.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Base64;

@Component
@Profile("prod")
public class CryptoService {
    private static final String ALG = "AES/GCM/NoPadding";
    private static final int IV_LEN = 12;     // 96-bit nonce
    private static final int TAG_BITS = 128;  // 16-byte tag

    private final SecretKey secretKey;
    private final SecureRandom secureRandom = new SecureRandom();

    public CryptoService(@Value("${financehub.secrets.master-key}") String b64Key) {
        if (StringUtils.isBlank(b64Key)) {
            throw new IllegalArgumentException("Master key must be provided in prod");
        }

        byte[] keyBytes = Base64.getDecoder().decode(b64Key);
        if (keyBytes.length < 32) {
            throw new IllegalArgumentException("Master key must be >= 32 bytes (256 bits) for AES-256");
        }
        this.secretKey = new SecretKeySpec(keyBytes, "AES");
    }

    public String encrypt(String plaintext) {
        try {
            byte[] iv = new byte[IV_LEN]; secureRandom.nextBytes(iv);
            Cipher c = Cipher.getInstance(ALG);
            c.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(TAG_BITS, iv));
            byte[] ct = c.doFinal(plaintext.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            ByteBuffer bb = ByteBuffer.allocate(iv.length + ct.length).put(iv).put(ct);
            return Base64.getEncoder().encodeToString(bb.array());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String decrypt(String blob) {
        try {
            byte[] all = Base64.getDecoder().decode(blob);
            byte[] iv = java.util.Arrays.copyOfRange(all, 0, IV_LEN);
            byte[] ct = java.util.Arrays.copyOfRange(all, IV_LEN, all.length);
            Cipher c = Cipher.getInstance(ALG);
            c.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(TAG_BITS, iv));
            byte[] pt = c.doFinal(ct);
            return new String(pt, java.nio.charset.StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Decrypt failed", e);
        }
    }
}
