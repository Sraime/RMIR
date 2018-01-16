package project_exemple.clients.car;

import project.registry.GlobalRegistry;
import project.registry.ReplicationRegistry;
import project_exemple.servers.car.Car;

public class MainCarClient {

  private static String SERVICE_NAME = "Car";
  private static String SERVICE_HOST = "localhost";

  public static void main(String[] args) throws Exception {

    // locate the registry that runs on the remote object's server
    ReplicationRegistry registry = GlobalRegistry.getRegistry(SERVICE_HOST);

    // retrieve the stub of the remote object by its name
    Car car = (Car) registry.lookup(SERVICE_NAME);

    System.out.println("client: car engine is " + car.getEngine());

  }

}
