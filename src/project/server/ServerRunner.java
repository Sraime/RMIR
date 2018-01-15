package project.server;

import project.registry.remote.TypedRemote;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ServerRunner {

    public static void runRemoteServer(TypedRemote tr) throws RemoteException, NotBoundException {
        RemoteServer rs = new ThreadedServer(tr);
        rs.start();
    }
}
