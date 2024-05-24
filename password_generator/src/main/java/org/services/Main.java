package org.services;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        // How to generate verification and challenge for security purposes
        //code challenge method = sha-256
        System.out.println("Hello world!");
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes1 = new byte[32];
        secureRandom.nextBytes(bytes1);
        String codeVerifier = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes1);
    MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        byte[] bytes2 = messageDigest.digest(codeVerifier.getBytes());
        String  codeChallenge = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes2);
        System.out.println("challenge= "+codeChallenge+"\n"+"verifier= "+codeVerifier);

    }
}