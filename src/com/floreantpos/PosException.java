package com.floreantpos;

public class PosException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public PosException() {
    super();
  }

  public PosException(String arg0) {
    super(arg0);
  }

  public PosException(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }

  public PosException(Throwable arg0) {
    super(arg0);
  }

}
