package it.unimore.fum.iot.utils.types;

import org.eclipse.californium.core.WebLink;

import java.util.Set;

public class EndpointDescriptor {
    private final String ep;
    private final String base;
    private final Set<WebLink> links;

    public EndpointDescriptor(String ep, String base, Set<WebLink> links) {
        this.ep = ep;
        this.base = base;
        this.links = links;
    }

    public String getEp() {
        return ep;
    }

    public String getBase() {
        return base;
    }

    public Set<WebLink> getLinks() {
        return links;
    }
}
