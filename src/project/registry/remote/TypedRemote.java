package project.registry.remote;

import project.registry.UniqueRemote;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TypedRemote extends Remote {
    Class<? extends UniqueRemote> getType() throws RemoteException;
}
