package com.acme.financehub.common;

public class Exceptions {
  public static class NotFoundException extends RuntimeException {
    public NotFoundException(String msg) { super(msg); }
  }
  public static class ForbiddenException extends RuntimeException {
    public ForbiddenException(String msg) { super(msg); }
  }
  public static class BadRequestException extends RuntimeException {
    public BadRequestException(String msg) { super(msg); }
  }
}
