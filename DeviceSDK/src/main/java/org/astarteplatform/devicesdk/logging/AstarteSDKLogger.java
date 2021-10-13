package org.astarteplatform.devicesdk.logging;

/** Interface class for all the AstarteSDK logger implementations. */
public interface AstarteSDKLogger {

  /**
   * Error level log message
   *
   * @param msg The log message
   */
  public void error(String msg);

  /**
   * Warning level log message
   *
   * @param msg The log message
   */
  public void warning(String msg);

  /**
   * Info level log message
   *
   * @param msg The log message
   */
  public void info(String msg);

  /**
   * Debug level log message
   *
   * @param msg The log message
   */
  public void debug(String msg);
}
