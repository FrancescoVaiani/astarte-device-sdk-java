package org.astarteplatform.devicesdk.android.examples;

import org.astarteplatform.devicesdk.AstarteMessageListener;
import org.astarteplatform.devicesdk.logging.AstarteSDKLogManager;
import org.astarteplatform.devicesdk.logging.AstarteSDKLogger;

public class ExampleMessageListener implements AstarteMessageListener {
  AstarteSDKLogger logger = AstarteSDKLogManager.INSTANCE.getLogger();
  private MainActivity mActivity;

  public ExampleMessageListener(MainActivity activity) {
    mActivity = activity;
  }

  @Override
  public void onConnected() {
    /*
     * This function gets called when the device establishes the connection
     * with the broker.
     *
     * When the connection is established, we enable the "Send ping" button.
     */
    logger.info("Device connected");
    mActivity.enablePingButton(true);
  }

  @Override
  public void onDisconnected(Throwable cause) {
    /*
     * This function gets called when the device loses the connection with the
     * broker.
     *
     * If the connection is lost, we disable the "Send ping" button.
     */
    logger.info("Device disconnected: " + cause.getMessage());
    mActivity.enablePingButton(false);
  }

  @Override
  public void onFailure(Throwable cause) {
    /*
     * This function gets called when the device encounters an error during its
     * lifetime.
     */
    logger.warning("Device failure: " + cause.getMessage());
  }
}
