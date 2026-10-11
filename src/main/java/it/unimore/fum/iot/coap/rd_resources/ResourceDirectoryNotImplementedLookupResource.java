package it.unimore.fum.iot.coap.rd_resources;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;

/**
 * This is a simple placeholder class used to tell the client this specific lookup option hasn't been implemented yet!
 */
public class ResourceDirectoryNotImplementedLookupResource extends CoapResource {
    public ResourceDirectoryNotImplementedLookupResource(String name) {
        super(name);
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        exchange.respond(CoAP.ResponseCode.NOT_IMPLEMENTED);
    }
}
