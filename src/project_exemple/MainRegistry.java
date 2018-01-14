package project_exemple;

import project.registry.GlobalRegistry;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

public class MainRegistry {

    public synchronized static void main(String[] args) throws RemoteException, AlreadyBoundException, InterruptedException {
        GlobalRegistry.loadReagistry();
        MainRegistry.class.wait();
    }
}
