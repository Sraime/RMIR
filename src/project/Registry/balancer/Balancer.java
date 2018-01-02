package project.Registry.balancer;

import project.Registry.UniqueRemote;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Balancer {
    public void addRessource(UniqueRemote r);
    public void removeRessource(String id) throws RemoteException, NotBoundException;
    public boolean containRessouce(String id) throws RemoteException;
    public UniqueRemote getNext();
}
