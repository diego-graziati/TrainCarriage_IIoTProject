package it.unimore.fum.iot.coap.rd_resources;

import it.unimore.fum.iot.utils.types.EndpointDescriptor;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.WebLink;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.server.resources.CoapExchange;

import java.util.Map;

public class ResourceDirectoryLookupResResource extends CoapResource {

    private final Map<String, EndpointDescriptor> endpointRegistry;

    public ResourceDirectoryLookupResResource(String name, Map<String, EndpointDescriptor> endpointRegistry) {
        super(name);

        this.endpointRegistry = endpointRegistry;
    }

    @Override
    public void handleGET(CoapExchange exchange) {
        try {
            //STEP 1: check for rt/if filters. If none, everything is returned
            String rtFilter = null;
            String ifFilter = null;

            for (String query: exchange.getRequestOptions().getUriQuery()) {
                if (query.startsWith("rt=")) {
                    rtFilter = query.substring("rt=".length());
                }
                if (query.startsWith("if=")) {
                    ifFilter = query.substring("if=".length());
                }
            }

            //TIP 1: change CoRE regex to Java regex. If a regex is null, meaning the filter hasn't been specified
            //inside the request's Uri Query, it is treated as if all possible results are accepted.
            //This implementation should be changed in favor of Pattern and the regex should be sanitized to avoid
            //regex injections (It's a university project, I hope it's not needed...).
            String rtRegex = (rtFilter != null) ? rtFilter.replace("*", ".*") : null;
            String ifRegex = (ifFilter != null) ? ifFilter.replace("*", ".*") : null;

            StringBuilder response = new StringBuilder();

            //STEP 2: let's cycle through our endpoints to find the ones that meet the filters criteria
            for (EndpointDescriptor epDesc: endpointRegistry.values()) {

                for (WebLink link: epDesc.getLinks()) {
                    //TIP2: read TIP 1 to understand why it's been done this way.
                    boolean matchesRt = (rtRegex == null);
                    boolean matchesIf = (ifRegex == null);

                    StringBuilder rtBuffer = new StringBuilder();
                    StringBuilder ifBuffer = new StringBuilder();

                    for (String rtDesc: link.getAttributes().getResourceTypes()) {
                        rtBuffer.append(";rt=\"").append(rtDesc).append("\"");
                        if (!matchesRt) {
                            if (rtDesc.matches(rtRegex)) {
                                matchesRt = true;
                            }
                        }
                    }

                    for (String ifDesc: link.getAttributes().getInterfaceDescriptions()) {
                        rtBuffer.append(";rt=\"").append(ifDesc).append("\"");
                        if (!matchesIf) {
                            if (ifDesc.matches(ifRegex)) {
                                matchesIf = true;
                            }
                        }
                    }

                    if (matchesRt && matchesIf) {
                        if (response.length() > 0) {
                            response.append(",");
                        }

                        String fullUri = epDesc.getBase() + (link.getURI().startsWith("/") ? "" : "/") + link.getURI();

                        response.append("<").append(fullUri).append(">");

                        response.append(rtBuffer);
                        response.append(ifBuffer);
                    }
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
