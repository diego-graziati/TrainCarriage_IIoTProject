package it.unimore.fum.iot.coap.rd_resources;

import it.unimore.fum.iot.utils.types.EndpointDescriptor;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.util.Map;

public class ResourceDirectoryLookupEpResource extends CoapResource {

    private final Map<String, EndpointDescriptor> endpointRegistry;

    public ResourceDirectoryLookupEpResource(String name, Map<String, EndpointDescriptor> endpointRegistry) {
        super(name);

        this.endpointRegistry = endpointRegistry;
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        try {
            String epFilter = null;

            for (String query: exchange.getRequestOptions().getUriQuery()) {
                if (query.startsWith("ep=")) {
                    epFilter = query.substring("ep=".length());
                }
            }

            //TIP 1: if no filter has been specified then all endpoints are accepted and returned.
            //This implementation should be changed in favor of Pattern and the regex should be sanitized to avoid
            //regex injections (I hope it doesn't happen, if it does I guess it's life).
            String regex = (epFilter != null) ? epFilter.replace("*", ".*") : null;

            StringBuilder response = new StringBuilder();

            for (EndpointDescriptor epDesc: endpointRegistry.values()) {
                if (regex == null || epDesc.getEp().matches(regex)) {
                    if (response.length() > 0) {
                        response.append(",");
                    }
                    response.append("<").append(epDesc.getBase()).append(">;ep=\"").append(epDesc.getEp()).append("\"");
                }
            }

            if (!response.toString().isEmpty()) {
                exchange.respond(CoAP.ResponseCode.CONTENT, response.toString(), MediaTypeRegistry.APPLICATION_LINK_FORMAT);
            } else {
                exchange.respond(CoAP.ResponseCode.NOT_FOUND);
            }
        } catch (Exception e) {
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
