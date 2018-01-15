package project_exemple.clients.car;

import project.registry.GlobalRegistry;
import project.registry.ReplicationRegistry;
import project_exemple.servers.car.Car;

/**
 * Client program.
 *
 * Note: For the the client to retrieve the stub of the remote object, it needs
 * to know: (1) what the name of the object is, (2) which machine hosts the
 * remote object.
 *
 */
public class MainCarClient {

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
    ReplicationRegistry registry = GlobalRegistry.getRegistry(SERVICE_HOST);
    System.out.println("client: retrieved registry");

    // retrieve the stub of the remote object by its name
    Car car = (Car) registry.newLookup(SERVICE_NAME);
    System.out.println("client: retrieved Car stub");

    System.out.println("client: car engine is " + car.getEngine());
    System.out.println("client: car destination is " + car.getDestination());

    // call the remote object to perform sorts and reverse sorts
    String engine = "V8";
    System.out.println("client: sending " + engine);

    car.changeEngine(engine);
    System.out.println("client: received " + car.getEngine());

    String destination = "Paris";
    System.out.println("client: sending " + destination);

    car.driveTo(destination);
    System.out.println("client: received " + car.getDestination());

    // main terminates here
    System.out.println("client: exiting");

  }

}
