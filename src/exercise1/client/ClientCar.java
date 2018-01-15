package exercise1.client;

import exercise1.models.Car;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ClientCar {

    //
    // CONSTANTS
    //
    private static String SERVICE_NAME = "Car";
    private static String SERVICE_HOST = "localhost";

    //
    // MAIN
    //
    public static void main(String[] args) throws Exception {

        // locate the registry that runs on the remote object's server
        Registry registry = LocateRegistry.getRegistry(SERVICE_HOST);
        System.out.println("client: retrieved registry");

        // retrieve the stub of the remote object by its name
        Car c = (Car) registry.lookup(SERVICE_NAME);
        System.out.println("client: retrieved Sorter stub");

        String engine = c.getEngine();
        System.out.println("client: received " + engine);

        // call the remote object to perform sorts and reverse sorts
        engine = "V8";
        System.out.println("client: sending " + engine);

        c.changeEngine(engine);

        engine = c.getEngine();
        System.out.println("client: received " + engine);

        // main terminates here
        System.out.println("client: exiting");

    }
}
