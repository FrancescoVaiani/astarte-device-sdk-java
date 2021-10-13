package org.astarteplatform.devicesdk.logging;

import com.j256.ormlite.logger.LocalLog;
import java.util.logging.*;

/**
 * Implementation of {@link AstarteSDKLogger} for the Android module. It wraps the standard {@link
 * Logger} Java library. It is possible to set the log level using the
 * `org.astarteplatform.devicesdk.logging.level` VM property or programmatically by using the
 * setLevel function. Acceptable values are: DEBUG, INFO, WARNING, ERROR and OFF. By default, it
 * handles the logging level of {@link LocalLog} for a consistent log level through all libraries.
 */
public class AstarteSDKLoggerImpl implements AstarteSDKLogger {
  private final Logger logger;

  public AstarteSDKLoggerImpl() {
    final Level DEFAULT_LEVEL = systemDefaultLevel();
    final BaseFormatter baseFormatter = new BaseFormatter();

    final StreamHandler handler =
        new StreamHandler(System.out, baseFormatter) {
          @Override
          public synchronized void publish(final LogRecord record) {
            super.publish(record);
            flush();
          }
        };

    this.logger = Logger.getLogger(this.getClass().getName());
    this.logger.setLevel(DEFAULT_LEVEL);
    this.logger.setUseParentHandlers(false);
    this.logger.addHandler(handler);
  }

  /**
   * Initialize the default logging level using the `org.astarteplatform.devicesdk.logging.level` VM
   * property. Example: `java -jar -D org.astarteplatform.devicesdk.logging.level=warning
   * [jar-file-name]`.
   *
   * <p>Possible values [debug, info, warning, severe, off] default: info
   *
   * @return The default logging level according to `org.astarteplatform.devicesdk.logging.level` or
   *     Level.INFO
   */
  private static Level systemDefaultLevel() {
    final String systemDefinedLevel =
        System.getProperty("org.astarteplatform.devicesdk.logging.level");
    if (systemDefinedLevel == null || systemDefinedLevel.isEmpty()) {
      System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "INFO");
      return Level.INFO;
    }
    switch (systemDefinedLevel.toLowerCase()) {
      case "debug":
        System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "DEBUG");
        return Level.CONFIG;
      case "warning":
        System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "WARNING");
        return Level.WARNING;
      case "error":
        System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "ERROR");
        return Level.SEVERE;
      case "off":
        System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "ERROR");
        return Level.OFF;
      default:
        System.setProperty(LocalLog.LOCAL_LOG_LEVEL_PROPERTY, "INFO");
        return Level.INFO;
    }
  }

  public void error(String msg) {
    log(Level.SEVERE, msg);
  }

  public void warning(String msg) {
    log(Level.WARNING, msg);
  }

  public void info(String msg) {
    log(Level.INFO, msg);
  }

  public void debug(String msg) {
    log(Level.CONFIG, msg);
  }

  private void log(Level level, String msg) {
    final StackTraceElement caller = Thread.currentThread().getStackTrace()[3];
    this.logger.logp(level, "", caller.getMethodName(), msg);
  }

  /**
   * Programmatically set the logging display level
   * @param level The new logging level
   */
  public void setLevel(Level level) {
    if (this.logger.getLevel().equals(level)) {
      return;
    }
    this.warning(String.format("Changed logger to %s level", level.getName()));
    this.logger.setLevel(level);
  }
}
