package project_exemple;

import project.registry.GlobaleRegistry;
import project.registry.ReplicationRegistry;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class MainRegistry {

    public synchronized static void main(String[] args) throws RemoteException, AlreadyBoundException, InterruptedException {
        GlobaleRegistry.loadReagistry();
        MainRegistry.class.wait();
    }
}
