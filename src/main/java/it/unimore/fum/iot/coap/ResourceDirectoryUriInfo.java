package it.unimore.fum.iot.coap;

import it.unimore.fum.iot.utils.tools.JsonConfigLoader;

public class ResourceDirectoryUriInfo {
    public final String RESOURCE_DIRECTORY_ADDRESS;
    public final int RESOURCE_DIRECTORY_PORT;

    public ResourceDirectoryUriInfo(String configPath) {
        JsonConfigLoader<ResourceDirectoryUriInfo> loader = new JsonConfigLoader<>(configPath, ResourceDirectoryUriInfo.class);
        ResourceDirectoryUriInfo resourceDirectoryUriInfo = loader.load();

        this.RESOURCE_DIRECTORY_ADDRESS = resourceDirectoryUriInfo.RESOURCE_DIRECTORY_ADDRESS;
        this.RESOURCE_DIRECTORY_PORT = resourceDirectoryUriInfo.RESOURCE_DIRECTORY_PORT;
    }
}
