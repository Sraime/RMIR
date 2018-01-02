package project;

import project.Registry.GlobaleRegistry;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MainRegistry {

    public static final int R_PORT = 1099;
    public static final String GR_KEY = "GR";
    public synchronized static void main(String[] args) throws RemoteException, AlreadyBoundException, InterruptedException {

        Registry globalRegistry = new GlobaleRegistry();
        System.out.println("globale registry created");
        Registry registry = LocateRegistry.createRegistry(R_PORT);
        System.out.println("registry listenning on port " + R_PORT);
        globalRegistry = (Registry) UnicastRemoteObject.exportObject(globalRegistry,0);
        registry.bind(GR_KEY, globalRegistry);
        System.out.println("registry binded with key " + GR_KEY);
        System.out.println("registries are ready to be used ");

        MainRegistry.class.wait();
    }
}
