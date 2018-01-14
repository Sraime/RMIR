package project.registry;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UniqueRemote extends Remote {

    public String getId() throws RemoteException;

    public Remote getPayload() throws RemoteException;
}
