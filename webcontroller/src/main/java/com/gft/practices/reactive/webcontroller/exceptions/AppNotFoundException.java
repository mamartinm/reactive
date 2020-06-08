package com.gft.practices.reactive.webcontroller.exceptions;

import com.gft.practices.reactive.beans.exceptions.BaseAppException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
@AllArgsConstructor
public class AppNotFoundException extends BaseAppException {

  public AppNotFoundException(String mensaje) {
    super(mensaje);
  }

}