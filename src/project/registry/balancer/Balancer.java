package project.registry.balancer;

import project.registry.UniqueRemote;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

public interface Balancer {

    public UniqueRemote getNext(List<UniqueRemote> remotes);
}
