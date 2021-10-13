package org.astarteplatform.devicesdk.generic.examples;

import org.astarteplatform.devicesdk.AstarteMessageListener;
import org.astarteplatform.devicesdk.logging.AstarteSDKLogManager;
import org.astarteplatform.devicesdk.logging.AstarteSDKLogger;

public class ExampleMessageListener implements AstarteMessageListener {
  final AstarteSDKLogger logger = AstarteSDKLogManager.INSTANCE.getLogger();

  public void onConnected() {
    /*
     * This function gets called when the device establishes the connection
     * with the broker.
     */
    logger.info("Device connected");
  }

  public void onDisconnected(Throwable cause) {
    /*
     * This function gets called when the device loses the connection with the
     * broker.
     */
    logger.info("Device disconnected: " + cause.getMessage());
  }

  public void onFailure(Throwable cause) {
    /*
     * This function gets called when the device encounters an error during its
     * lifetime.
     */
    logger.error("Device failure: " + cause.getMessage());
  }
}
