package project.registry.balancer;

import project.registry.UniqueRemote;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

public class FirstInstanceBalancer implements Balancer {

    @Override
    public UniqueRemote getNext(List<UniqueRemote> remotes) {
        return remotes.size() == 0 ? null : remotes.get(0);
    }
}