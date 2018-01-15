package project.registry.balancer;

import project.registry.UniqueRemote;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public interface Balancer {
    void addRessource(UniqueRemote remote);

    void removeRessource(String id) throws RemoteException, NotBoundException;

    List<UniqueRemote> getRessources();

    boolean containRessouce(String id) throws RemoteException;

    UniqueRemote getNext();
}
