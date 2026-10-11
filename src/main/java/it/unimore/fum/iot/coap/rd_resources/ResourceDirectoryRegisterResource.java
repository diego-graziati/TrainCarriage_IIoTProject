package it.unimore.fum.iot.coap.rd_resources;

import it.unimore.fum.iot.utils.types.EndpointDescriptor;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.WebLink;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.LinkFormat;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.util.Map;
import java.util.Set;

public class ResourceDirectoryRegisterResource extends CoapResource {

    private final Map<String, EndpointDescriptor> endpointRegistry;

    public ResourceDirectoryRegisterResource(String name, Map<String, EndpointDescriptor> endpointRegistry) {
        super(name);

        this.endpointRegistry = endpointRegistry;
    }

    @Override
    public void handlePOST(CoapExchange exchange) {

        try {
            if (exchange.getRequestPayload() != null) {
                String payload = exchange.getRequestText();
                Set<WebLink> links = LinkFormat.parse(payload);

                String ep = null;
                String base = null;

                for (String queryParam: exchange.getRequestOptions().getUriQuery()) {
                    if (queryParam.startsWith("ep=")) {
                        ep = queryParam.substring("ep=".length());
                    } else if (queryParam.startsWith("base=")) {
                        base = queryParam.substring("base=".length());
                    }
                }

                if (ep != null && !ep.isEmpty()) {

                    if (base == null || base.isEmpty()) {
                        //Fallback
                        base = "coap://" + exchange.getSourceAddress().getHostAddress() + ":" + CoAP.DEFAULT_COAP_PORT;
                    }

                    endpointRegistry.put(ep, new EndpointDescriptor(ep, base, links));

                    exchange.setLocationPath("/rd/" + ep);
                    exchange.respond(CoAP.ResponseCode.CREATED);
                } else {
                    exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
                }
            } else {
                exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
            }
        } catch (Exception e) {
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
        }
    }
}
