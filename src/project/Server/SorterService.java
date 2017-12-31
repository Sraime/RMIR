package project.Server;

import exercise1.Sorter;
import exercise1.server.SimpleSorter;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class SorterService extends RemoteServer{

    public static final String KEY_SORTER_REGISTRY = "Sorter";

    public SorterService() throws RemoteException, NotBoundException {
        super(KEY_SORTER_REGISTRY);
    }

    @Override
    public void run() {

        Sorter sorter = new SimpleSorter();
        try {
            this.saveRemoteObject(sorter);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
