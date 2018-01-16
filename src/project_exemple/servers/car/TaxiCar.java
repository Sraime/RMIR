package project_exemple.servers.car;

import project.Service.UUIDUniqueRemote;
import project.registry.UniqueRemote;
import project.registry.remote.TypedRemote;

import java.io.Serializable;

public class TaxiCar extends UUIDUniqueRemote implements Car, Serializable {

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
    public Class<? extends UniqueRemote> getType() {
        return Car.class;
    }

}
