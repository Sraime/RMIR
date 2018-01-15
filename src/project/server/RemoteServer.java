package project.server;

import project.registry.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.UUID;

public abstract class RemoteServer extends Thread {

    private static final String HOST_GLOBAL_REGISTRY = "localhost";

    private ReplicationRegistry registry;
    private final String serveurId;
    private final String keyRegistry;

    protected RemoteServer() throws RemoteException, NotBoundException {
        this(null);
    }

    protected RemoteServer(String keyRegistry) throws RemoteException, NotBoundException {
        this.serveurId = "server-" + UUID.randomUUID();
        this.keyRegistry = (keyRegistry == null || keyRegistry.equals("")) ? this.serveurId : keyRegistry;
        this.registry = GlobalRegistry.getRegistry(HOST_GLOBAL_REGISTRY);
        System.out.println(this.serveurId + " created with key " + this.keyRegistry);
    }

    protected final void saveRemoteObject(UniqueRemote tr) throws RemoteException {
        UniqueRemote stub = (UniqueRemote) UnicastRemoteObject.exportObject(tr, 0);
        System.out.println(this.serveurId + " generated skeleton and stub for '" + this.keyRegistry + "'");
        this.registry.rebind(keyRegistry, stub);
    }

    public abstract void run();

}
