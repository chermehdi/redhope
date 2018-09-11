package com.mql.redhope.domain.models;

/**
 * @author mehdithe
 */
public enum BloodType {
  A_PLUS,
  A_MINUS,
  B_PLUS,
  B_MINUS,
  AB_PLUS,
  AB_MINUS,
  UNSET,
  O_PLUS,
  O_MINUS;

  public static BloodType from(String type) {
    switch (type) {
      case "A_PLUS":
        return A_PLUS;
      case "A_MINUS":
        return A_MINUS;
      case "B_MINUS":
        return B_MINUS;
      case "B_PLUS":
        return B_PLUS;
      case "AB_PLUS":
        return AB_PLUS;
      case "O_MINUS":
        return O_MINUS;
      case "O_PLUS":
        return O_PLUS;
      case "AB_MINUS":
        return AB_MINUS;
      default:
        return UNSET;
    }
  }
}
