package org.astarteplatform.devicesdk.android.examples;

import org.astarteplatform.devicesdk.logging.AstarteSDKLogManager;
import org.astarteplatform.devicesdk.logging.AstarteSDKLogger;
import org.astarteplatform.devicesdk.protocol.AstarteAggregateDatastreamEvent;
import org.astarteplatform.devicesdk.protocol.AstarteDatastreamEvent;
import org.astarteplatform.devicesdk.protocol.AstarteGlobalEventListener;
import org.astarteplatform.devicesdk.protocol.AstartePropertyEvent;

public class ExampleGlobalEventListener extends AstarteGlobalEventListener {
  AstarteSDKLogger logger = AstarteSDKLogManager.INSTANCE.getLogger();
  private static final String COMMANDS_INTERFACE =
      "org.astarte-platform.genericcommands.ServerCommands";
  private MainActivity mActivity;

  public ExampleGlobalEventListener(MainActivity activity) {
    mActivity = activity;
  }

  @Override
  public void valueReceived(AstarteDatastreamEvent e) {
    /*
     * This function gets called when the device receives data on a server owned
     * datastream interface with individual aggregation.
     *
     * We handle data coming from org.astarte-platform.genericcommands.ServerCommands/command
     * as special case, displaying it in the commands TextView.
     */
    if (e.getInterfaceName().equals(COMMANDS_INTERFACE) && e.getPath().equals("/command")) {
      mActivity.setCommandsText(e.getValueString());
    } else {
      /*
       * Otherwise, we just print what we receive
       */
      logger.info(
          "Received datastream value on interface "
              + e.getInterfaceName()
              + ", path: "
              + e.getPath()
              + ", value: "
              + e.getValue());
    }
  }

  @Override
  public void valueReceived(AstarteAggregateDatastreamEvent e) {
    /*
     * This function gets called when the device receives data on a server owned
     * datastream interface with object aggregation.
     */
    logger.info(
        "Received aggregate datastream value on interface "
            + e.getInterfaceName()
            + ", values: "
            + e.getValues());
  }

  @Override
  public void propertyReceived(AstartePropertyEvent e) {
    /*
     * This function gets called when the device receives data on a server owned
     * properties interface.
     */
    logger.info(
        "Received property on interface "
            + e.getInterfaceName()
            + ", path: "
            + e.getPath()
            + ", value: "
            + e.getValue());
  }

  @Override
  public void propertyUnset(AstartePropertyEvent e) {
    /*
     * This function gets called when the device receives an unset on a server owned
     * properties interface.
     */
    logger.info("Received unset on interface " + e.getInterfaceName() + ", path: " + e.getPath());
  }
}
