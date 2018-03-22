package com.mql.redhope.buisness.impl;

import com.mql.redhope.buisness.PasswordEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncoderImp implements PasswordEncoder {

  @Override
  public String encode(String password) {
    StringBuilder hash = new StringBuilder();
    password = "redhope" + password; // TODO: add salt
    try {
      MessageDigest sha = MessageDigest.getInstance("SHA-1");
      byte[] hashedBytes = sha.digest(password.getBytes());
      char[] digits =
          {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
      for (byte b : hashedBytes) {
        hash.append(digits[(b & 0xf0) >> 4]);
        hash.append(digits[b & 0x0f]);
      }
    } catch (NoSuchAlgorithmException ignored) {
    }
    return hash.toString();
  }


}
