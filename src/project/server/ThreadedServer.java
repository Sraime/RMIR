package project.server;

import project.registry.remote.TypedRemote;
import project.registry.replication.Replicated;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ThreadedServer extends RemoteServer {

    private TypedRemote remote;

    public ThreadedServer(TypedRemote rr) throws RemoteException, NotBoundException {
        super(rr.getType().getAnnotation(Replicated.class).key());
        this.remote = rr;
    }

    @Override
    public void run() {
        try {
            this.saveRemoteObject(remote);
        } catch (RemoteException e) {
            e.printStackTrace();

        }
    }
}
