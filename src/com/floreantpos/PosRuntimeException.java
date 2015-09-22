package com.floreantpos;

public class PosRuntimeException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public PosRuntimeException() {
    super();
  }

  public PosRuntimeException(String message) {
    super(message);
  }

  public PosRuntimeException(String message, Throwable cause) {
    super(message, cause);
  }

  public PosRuntimeException(Throwable cause) {
    super(cause);
  }

}
