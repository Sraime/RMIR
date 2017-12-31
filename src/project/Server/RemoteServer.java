package project.Server;

import exercise1.Sorter;
import project.MainRegistry;
import project.Registry.GlobaleRegistry;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

public abstract class RemoteServer extends Thread{

    private static final String HOST_GLOBAL_REGISTRY = "localhost";

    private Registry registry;
    private final String serveurId;
    private final String keyRegistry;

    protected RemoteServer() throws RemoteException, NotBoundException {
        this(null);
    }

    protected RemoteServer(String keyRegistry) throws RemoteException, NotBoundException {
        this.serveurId = "server-"+ UUID.randomUUID();
        this.keyRegistry = (keyRegistry == null || keyRegistry.equals("")) ? this.serveurId : keyRegistry;
        this.registry = GlobaleRegistry.getRegistry(HOST_GLOBAL_REGISTRY);
        //this.registry = LocateRegistry.getRegistry(HOST_GLOBAL_REGISTRY, MainRegistry.GR_PORT);
        System.out.println(this.serveurId+" registered with key "+this.keyRegistry);
    }

    protected final void saveRemoteObject(Remote obj) throws RemoteException {

        Remote stub = UnicastRemoteObject.exportObject(obj , 0);
        System.out.println(this.serveurId + " generated skeleton and stub for '"+this.keyRegistry+"'");
        this.registry.rebind(keyRegistry, stub);
    }

    public abstract void run();

}
