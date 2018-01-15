package project.registry.balancer;

import project.registry.UniqueRemote;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

public class RoundRobinBalancer implements Balancer {

    private Integer nextIndex;

    public RoundRobinBalancer() {
        this.nextIndex = 0;
    }

    @Override
    public UniqueRemote getNext(List<UniqueRemote> remotes) {
        if(remotes.size() == 0)
            return null;
        if(nextIndex > remotes.size()-1)
            nextIndex = 0;
        UniqueRemote r = remotes.get(this.nextIndex);
        this.nextIndex = (nextIndex + 1) % remotes.size();
        return r;
    }
}
