package project;

import project.Registry.GlobaleRegistry;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainRegistry {

    public static final int GR_PORT = 1100;
    public static final int R_PORT = 1099;
    public static final String GR_KEY = "GR";
    public synchronized static void main(String[] args) throws RemoteException, AlreadyBoundException, InterruptedException {

        System.out.println("globale registry listenning on port " + GR_PORT);
        Registry globalRegistry = new GlobaleRegistry(GR_PORT);
        System.out.println("registry listenning on port " + R_PORT);
        Registry registry = LocateRegistry.createRegistry(R_PORT);
        System.out.println("registry binded with key " + GR_KEY);
        registry.bind(GR_KEY, globalRegistry);
        System.out.println("registries are ready to be used ");

        MainRegistry.class.wait();
    }
}
