package org.astarteplatform.devicesdk.logging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogRecord;

class BaseFormatter extends Formatter {
  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

  private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd HH:mm:ss,SSS");

  @Override
  public String format(LogRecord record) {
    String logColour = ANSI_GREEN;
    if (record.getLevel() == Level.SEVERE) {
      logColour = ANSI_RED;
    } else if (record.getLevel() == Level.WARNING) {
      logColour = ANSI_YELLOW;
    } else if (record.getLevel() == Level.INFO) {
      logColour = ANSI_BLUE;
    }

    return String.format(
        "%s%s [%s] %s %s%s\n",
        logColour,
        sdf.format(new Date(record.getMillis())),
        record.getLevel().getName(),
        record.getSourceMethodName(),
        record.getMessage(),
        ANSI_RESET);
  }
}
