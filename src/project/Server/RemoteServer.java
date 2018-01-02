package project.Server;

import project.Registry.GlobaleRegistry;
import project.Registry.RemotePacket;
import project.Registry.UniqueRemote;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
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
        System.out.println(this.serveurId+" created with key "+this.keyRegistry);
    }

    protected final void saveRemoteObject(Remote obj) throws RemoteException {

        UniqueRemote stub = (UniqueRemote) UnicastRemoteObject.exportObject(new RemotePacket(obj), 0);
        System.out.println(this.serveurId + " generated skeleton and stub for '"+this.keyRegistry+"'");
        this.registry.rebind(keyRegistry, stub);
    }

    public abstract void run();

}
