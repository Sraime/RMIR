package project.registry;

import project.registry.remote.TypedRemote;

import java.rmi.RemoteException;

public interface UniqueRemote extends TypedRemote {
    String getId() throws RemoteException;
}
