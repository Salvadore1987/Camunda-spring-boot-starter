package uz.keysoft.camunda.spring.boot.starter.exception;

import org.springframework.http.HttpStatus;

public class CamundaRestException extends RuntimeException {

  private final HttpStatus status;
  private final String message;

  public CamundaRestException(HttpStatus status, String message) {
    this.status = status;
    this.message = String.format("Can't execute remote operation HTTP status is %d, message: %s", status.value(), message);
  }

  @Override
  public String getMessage() {
    return this.message;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
