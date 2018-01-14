package project.registry.balancer;

import project.registry.UniqueRemote;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

public class RoundRobinBalancer implements Balancer {

    private LinkedList<UniqueRemote> remotes;
    private Integer nextIndex;

    public RoundRobinBalancer() {
        this.remotes = new LinkedList<UniqueRemote>();
    }

    @Override
    public void addRessource(UniqueRemote r) {
        this.remotes.add(r);
        if (this.nextIndex == null)
            this.nextIndex = 0;
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
        if (this.remotes.size() == 0)
            this.nextIndex = null;
    }

    @Override
    public List<UniqueRemote> getRessources() {
        return remotes;
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
        UniqueRemote r = this.remotes.get(this.nextIndex);
        this.nextIndex = (nextIndex + 1) % this.remotes.size();
        return r;
    }
}
