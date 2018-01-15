package project.server;

import project.registry.UniqueRemote;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ServerRunner {

    public static void runRemoteServer(UniqueRemote tr) throws RemoteException, NotBoundException {
        RemoteServer rs = new ThreadedServer(tr);
        rs.start();
    }
}
