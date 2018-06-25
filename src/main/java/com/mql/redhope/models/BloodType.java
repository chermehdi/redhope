package com.mql.redhope.models;

/**
 * @author mehdithe
 */
public enum BloodType {
  A,
  B,
  AB,
  UNSET,
  O;

  public static BloodType from(String type) {
    switch (type) {
      case "A":
        return A;
      case "B":
        return B;
      case "AB":
        return AB;
      case "O":
        return O;
      default:
        return UNSET;
    }
  }
}
