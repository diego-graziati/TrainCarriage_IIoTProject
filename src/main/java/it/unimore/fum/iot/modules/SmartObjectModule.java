package it.unimore.fum.iot.modules;

import org.eclipse.californium.core.CoapServer;

public abstract class SmartObjectModule extends CoapServer implements ISmartObjectModule {
    public SmartObjectModule() {

    }

    public static void main(String[] args) {

    }

    public void launch() {
        this.start();

        this.getRoot().getChildren().forEach(resource -> {
            System.out.printf("Resource %s -> URI: %s (Observable: %b)%n", resource.getName(),
                    resource.getURI(), resource.isObservable());
        });
    }
}
