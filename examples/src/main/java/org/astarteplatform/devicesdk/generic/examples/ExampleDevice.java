package org.astarteplatform.devicesdk.generic.examples;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import java.util.Random;
import org.apache.commons.cli.*;
import org.astarteplatform.devicesdk.AstarteDevice;
import org.astarteplatform.devicesdk.generic.AstarteGenericDevice;
import org.astarteplatform.devicesdk.protocol.AstarteDeviceDatastreamInterface;
import org.astarteplatform.devicesdk.protocol.AstarteDevicePropertyInterface;
import org.joda.time.DateTime;

public class ExampleDevice {
  private static final String availableSensorsInterfaceName =
      "org.astarte-platform.genericsensors.AvailableSensors";
  private static final String valuesInterfaceName = "org.astarte-platform.genericsensors.Values";
  private static final String sensorUuid = "b2c5a6ed-ebe4-4c5c-9d8a-6d2f114fc6e5";

  public static void main(String[] args) throws Exception {
    /*
     *  Initialization of needed parameters, reading them from the command line
     */
    Options options = new Options();

    Option realmOpt = new Option("r", "realm", true, "The target Astarte realm");
    realmOpt.setRequired(true);
    options.addOption(realmOpt);

    Option deviceIdOpt = new Option("d", "device-id", true, "The device id for the Astarte Device");
    deviceIdOpt.setRequired(true);
    options.addOption(deviceIdOpt);

    Option credentialsSecretOpt =
        new Option(
            "c", "credentials-secret", true, "The credentials secret for the Astarte Device");
    credentialsSecretOpt.setRequired(true);
    options.addOption(credentialsSecretOpt);

    Option pairingUrlOpt =
        new Option(
            "p",
            "pairing-url",
            true,
            "The URL to reach Pairing API in the target Astarte instance");
    pairingUrlOpt.setRequired(true);
    options.addOption(pairingUrlOpt);

    CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();
    CommandLine cmd = null;

    try {
      cmd = parser.parse(options, args);
    } catch (ParseException e) {
      System.out.println(e.getMessage());
      formatter.printHelp("Astarte SDK Example", options);

      System.exit(1);
    }

    String realm = cmd.getOptionValue("realm");
    String deviceId = cmd.getOptionValue("device-id");
    String credentialsSecret = cmd.getOptionValue("credentials-secret");
    String pairingUrl = cmd.getOptionValue("pairing-url");

    /*
     * Astarte device creation
     *
     * We use h2 in memory as persistence in this example, but any DB compatible
     * with JDBC can be used.
     *
     * The interfaces supported by the device are populated by ExampleInterfaceProvider, see that
     * class for more details
     */
    JdbcConnectionSource connectionSource = new JdbcConnectionSource("jdbc:h2:mem:testDb");
    AstarteDevice device =
        new AstarteGenericDevice(
            deviceId,
            realm,
            credentialsSecret,
            new ExampleInterfaceProvider(),
            pairingUrl,
            connectionSource);
    /*
     * Connect listeners
     *
     * See ExampleMessageListener to listen for device connection, disconnection and failure.
     * See ExampleGlobalEventListener to listen for incoming data pushed from Astarte.
     */
    device.setAstarteMessageListener(new ExampleMessageListener());
    device.addGlobalEventListener(new ExampleGlobalEventListener());

    /*
     * Start the connection
     */
    device.connect();

    /*
     * Wait for device connection
     *
     * This can be handled asynchronously in the Message Listener.
     */
    while (!device.isConnected()) {
      Thread.sleep(100);
    }

    /*
     * Publish on a properties interface
     *
     * Retrieve the interface from the device and call setProperty on it.
     */
    AstarteDevicePropertyInterface availableSensorsInterface =
        (AstarteDevicePropertyInterface) device.getInterface(availableSensorsInterfaceName);

    availableSensorsInterface.setProperty(
        String.format("/%s/name", sensorUuid), "randomThermometer");
    availableSensorsInterface.setProperty(String.format("/%s/unit", sensorUuid), "°C");

    /*
     * Publish on a datastream interface
     *
     * Retrieve the interface from the device and call streamData on it.
     */
    AstarteDeviceDatastreamInterface valuesInterface =
        (AstarteDeviceDatastreamInterface) device.getInterface(valuesInterfaceName);

    Random r = new Random();
    String path = String.format("/%s/value", sensorUuid);

    while (true) {
      double value = 20 + 10 * r.nextDouble();
      System.out.println("Streaming value: " + value);
      valuesInterface.streamData(path, value, DateTime.now());
      Thread.sleep(500);
    }
  }
}