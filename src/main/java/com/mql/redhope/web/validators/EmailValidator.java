package com.mql.redhope.web.validators;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class EmailValidator implements Validator {

  @Override
  public void validate(FacesContext context, UIComponent component, Object value)
      throws ValidatorException {
    // TODO: add validation logic
    // email should not exist in the database
  }
}
