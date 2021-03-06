package project_exemple.clients.sorter;

import project.registry.GlobalRegistry;
import project.registry.ReplicationRegistry;
import project_exemple.servers.sorter.Sorter;

import java.util.Arrays;
import java.util.List;

public class MainSorterClient {

  private static String SERVICE_NAME = "Sorter";
  private static String SERVICE_HOST = "localhost";

  public static void main(String[] args) throws Exception {

    // locate the registry that runs on the remote object's server
    ReplicationRegistry registry = GlobalRegistry.getRegistry(SERVICE_HOST);
    System.out.println("client: retrieved registry");

    // retrieve the stub of the remote object by its name
    Sorter sorter = (Sorter) registry.lookup(SERVICE_NAME);
    System.out.println("client: retrieved Sorter stub");

    // call the remote object to perform sorts and reverse sorts
    List<String> list = Arrays.asList("3", "5", "1", "2", "4");
    System.out.println("client: sending " + list);

    list = sorter.sort(list);
    System.out.println("client: received " + list);

    list = Arrays.asList("mars", "saturne", "neptune", "jupiter");
    System.out.println("client: sending " + list);

    list = sorter.reverseSort(list);
    System.out.println("client: received " + list);

    // main terminates here
    System.out.println("client: exiting");

  }

}
