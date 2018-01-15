package project_exemple.servers.car;

import project.registry.remote.TypedRemote;
import project.service.RemoteRessource;

import java.io.Serializable;

public class CarPacket implements Car, Serializable, TypedRemote {

    private String engine = "V6";
    private String destination = "Villejuif";

    @Override
    public String getEngine() {
        return this.engine;
    }

    @Override
    public String getDestination() {
        return this.destination;
    }

    @Override
    public void changeEngine(String engine) {
        this.engine = engine;
    }

    @Override
    public void driveTo(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Car " + Thread.currentThread();
    }

    @Override
    public Class<? extends RemoteRessource> getType() {
        return Car.class;
    }
}
