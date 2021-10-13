package org.astarteplatform.devicesdk.logging;

/**
 * Class that handles the singleton of the AstarteSDKLogger implementation instance depending on the
 * current platform.
 *
 * <p>An implementation class of {@link AstarteSDKLogger} with the same name is put either in the
 * DeviceSDKGeneric and in the DeviceSDKAndroid module to ensure that only the correct standard log
 * library for the platform is used.
 */
public enum AstarteSDKLogManager {
  INSTANCE;

  private AstarteSDKLogger logger;

  /**
   * Initialize the singleton with the right {@link AstarteSDKLogger} implementation according to
   * the module in use.
   */
  AstarteSDKLogManager() {
    try {
      this.logger =
          (AstarteSDKLogger)
              Class.forName("org.astarteplatform.devicesdk.logging.AstarteSDKLoggerImpl")
                  .newInstance();
    } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Retrieve the current logger implementation.
   *
   * @return an implementation of {@link AstarteSDKLogger}
   */
  public AstarteSDKLogger getLogger() {
    return this.logger;
  }
}
