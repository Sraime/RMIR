package project.Server;

import project.Registry.RemotePacket;
import project.Services.SorterPacket;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class SorterService extends RemoteServer{

    public static final String KEY_SORTER_REGISTRY = "Sorter";

    public SorterService() throws RemoteException, NotBoundException {
        super(KEY_SORTER_REGISTRY);
    }

    @Override
    public void run() {

        SorterPacket sorter = new SorterPacket();
        try {
            this.saveRemoteObject(sorter);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
