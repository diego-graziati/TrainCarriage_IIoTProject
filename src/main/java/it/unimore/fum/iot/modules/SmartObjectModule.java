package it.unimore.fum.iot.modules;

import it.unimore.fum.iot.coap.ResourceDirectoryUriInfo;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.LinkFormat;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.elements.exception.ConnectorException;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.Logger;

public abstract class SmartObjectModule extends CoapServer implements ISmartObjectModule {
    private final String DEVICE_ID;
    private final ResourceDirectoryUriInfo RD_URI_INFO;

    protected final Logger LOGGER;
    private boolean failedToSubscribeToRD;

    public SmartObjectModule(String deviceId, String resourceDirectoryRdInfoConfigPath, String loggerGroupName) {
        super();
        this.DEVICE_ID = deviceId;
        this.RD_URI_INFO = new ResourceDirectoryUriInfo(resourceDirectoryRdInfoConfigPath);
        this.failedToSubscribeToRD = false;

        if (loggerGroupName != null) {
            this.LOGGER = Logger.getLogger(loggerGroupName + "." + deviceId);
        } else {
            this.LOGGER = Logger.getLogger(deviceId);
        }
        this.LOGGER.info("SmartObjectModule started");
    }

    @Override
    public synchronized void start() {
        try {
            super.start();

            //If the SmartObject is supposed to subscribe itself to a resource directory try to do so, otherwise proceed
            //with the standard start procedure
            if(this.RD_URI_INFO != null) {
                //Subscribe the endpoint to the Resource Directory.
                int port = this.getEndpoints().get(0).getAddress().getPort();
                String base = "coap://" + this.getEndpoints().get(0).getAddress().getHostString() + ":" + port;

                String linkFormatPayload = LinkFormat.serializeTree(this.getRoot());

                //Build the resource directory rd URI
                String registrationUri = "coap://" + this.RD_URI_INFO.RESOURCE_DIRECTORY_ADDRESS + ":" +
                        this.RD_URI_INFO.RESOURCE_DIRECTORY_PORT + "/rd?ep=" + this.DEVICE_ID + "&base=" + base;
                CoapClient coapClient = new CoapClient(registrationUri);
                try {
                    CoapResponse response = coapClient.post(linkFormatPayload, MediaTypeRegistry.APPLICATION_LINK_FORMAT);

                    if (response != null && response.isSuccess()) {
                        //HAPPY :D
                        this.LOGGER.info("Smart object successfully subscribed to the Resource Directory");
                        this.failedToSubscribeToRD = false;
                    } else {
                        this.LOGGER.severe("Unable to subscribe the smart object to the Resource Directory. Stopping...");
                        this.failedToSubscribeToRD = true;
                    }
                } finally {
                    coapClient.shutdown();
                }
            }
        } catch (ConnectorException | IOException e) {
            this.LOGGER.severe("Unable to start the smart object because of an exception:\n" + e.getMessage());
        }
    }

    @Override
    public synchronized void stop() {
        super.stop();
    }

    public boolean isFailedToSubscribeToRD() {
        return this.failedToSubscribeToRD;
    }
}
