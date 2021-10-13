package org.astarteplatform.devicesdk.logging;

import android.util.Log;

/**
 * Implementation of {@link AstarteSDKLogger} for the Android module. It wraps the standard {@link
 * Log} Android library
 */
public class AstarteSDKLoggerImpl implements AstarteSDKLogger {
  private final String TAG = "AstarteSDK";

  @Override
  public void error(String msg) {
    Log.e(TAG, msg);
  }

  @Override
  public void warning(String msg) {
    Log.w(TAG, msg);
  }

  @Override
  public void info(String msg) {
    Log.i(TAG, msg);
  }

  @Override
  public void debug(String msg) {
    Log.d(TAG, msg);
  }
}
