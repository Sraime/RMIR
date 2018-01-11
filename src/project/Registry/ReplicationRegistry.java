package project.Registry;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public interface ReplicationRegistry extends Registry{

    void unbindRemote(String key, String id) throws RemoteException, NotBoundException;
}
