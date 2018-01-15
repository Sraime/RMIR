package project_exemple;

import project.server.ServerRunner;
import project_exemple.servers.car.TaxiCar;
import project_exemple.servers.sorter.SimpleSorter;

public class MainServers {

    public static void main(String[] args){
        try {
            ServerRunner.runRemoteServer(new SimpleSorter());
            ServerRunner.runRemoteServer(new TaxiCar());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
