package project.registry.balancer;

import project.registry.UniqueRemote;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

public class FirstInstanceBalancer implements Balancer {

    private LinkedList<UniqueRemote> remotes;

    public FirstInstanceBalancer() {
        this.remotes = new LinkedList<UniqueRemote>();
    }

    @Override
    public void addRessource(UniqueRemote r) {
        this.remotes.add(r);
    }

    @Override
    public void removeRessource(String id) throws RemoteException, NotBoundException {
        UniqueRemote obj = null;
        for (UniqueRemote r : remotes)
            if (r.getId().equals(id))
                obj = r;
        if (obj == null)
            throw new NotBoundException();
        this.remotes.remove(obj);
    }

    @Override
    public List<UniqueRemote> getRessources() {
        return null;
    }

    @Override
    public boolean containRessouce(String id) throws RemoteException {
        for (UniqueRemote r : remotes)
            if (r.getId().equals(id))
                return true;
        return false;
    }

    @Override
    public UniqueRemote getNext() {
        return remotes.get(0);
    }
}
