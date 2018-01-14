package project.registry.balancer;

import project.registry.UniqueRemote;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public interface Balancer {
    public void addRessource(UniqueRemote remote);

    public void removeRessource(String id) throws RemoteException, NotBoundException;

    public List<UniqueRemote> getRessources();

    public boolean containRessouce(String id) throws RemoteException;

    public UniqueRemote getNext();
}
