package net.frodwith.jaque.exception;

import com.oracle.truffle.api.nodes.SlowPathException;

public abstract class RequireException extends SlowPathException {
  protected final Object value;

  protected RequireException(String kind, Object value) {
    super(buildMessage(kind, value));
    this.value = value;
  }

  protected RequireException(String kind, Object value, Throwable cause) {
    super(buildMessage(kind, value), cause);
    this.value = value;
  }

  private static String buildMessage(String kind, Object value) {
    return kind + " required, got " + value.getClass().toString();
  }
}