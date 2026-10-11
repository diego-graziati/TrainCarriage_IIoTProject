package it.unimore.fum.iot.coap;

import it.unimore.fum.iot.coap.rd_resources.ResourceDirectoryLookupEpResource;
import it.unimore.fum.iot.coap.rd_resources.ResourceDirectoryLookupResResource;
import it.unimore.fum.iot.coap.rd_resources.ResourceDirectoryNotImplementedLookupResource;
import it.unimore.fum.iot.coap.rd_resources.ResourceDirectoryRegisterResource;
import it.unimore.fum.iot.utils.types.EndpointDescriptor;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ResourceDirectory extends CoapServer {
    private final Map<String, EndpointDescriptor> endpointRegistry;

    public ResourceDirectory(int port) {
        super(port);

        this.endpointRegistry = new ConcurrentHashMap<>();

        this.add(new ResourceDirectoryRegisterResource("rd", this.endpointRegistry));

        CoapResource rdLookup = new CoapResource("rd-lookup");

        rdLookup.add(new ResourceDirectoryLookupEpResource("ep", this.endpointRegistry));
        rdLookup.add(new ResourceDirectoryLookupResResource("res", this.endpointRegistry));
        rdLookup.add(new ResourceDirectoryNotImplementedLookupResource("gp"));
        rdLookup.add(new ResourceDirectoryNotImplementedLookupResource("d"));

        this.add(rdLookup);
    }
}
