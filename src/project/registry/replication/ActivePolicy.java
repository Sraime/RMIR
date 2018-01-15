package project.registry.replication;

import project.registry.UniqueRemote;
import project.registry.replication.consortium.Consortium;
import project.registry.replication.consortium.ConsortiumFactory;
import project.registry.replication.consortium.ConsortiumType;
import project.registry.replication.consortium.UnresolvableConsortiumException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

public class ActivePolicy implements ReplicationPolicy {

    private UniqueRemote statelessTarget;
    private List<UniqueRemote> remotes;

    ActivePolicy(UniqueRemote statelessTarget, List<UniqueRemote> remotes) {
        this.statelessTarget = statelessTarget;
        this.remotes = remotes;
    }

    @Override
    public Object applyPolicy(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException, RemoteException {
        if(!method.isAnnotationPresent(Stateful.class)) {
            return this.applyStateless(method, args);
        } else {
            return this.applyStateful(method, args);
        }
    }

    @Override
    public Object applyStateless(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException, RemoteException {
        System.out.println("[REPLICATION] applying the Active replication in stateless mode");
        System.out.println("[REPLICATION] contacting the service with id " + statelessTarget.getId());
        return method.invoke(statelessTarget, args);
    }

    @Override
    public Object applyStateful(Method method, Object[] args) throws RemoteException, InvocationTargetException, IllegalAccessException {
        System.out.println("[REPLICATION] applying the Active replication in stateful mode");
        LinkedList<Object> results = new LinkedList<Object>();
        for (UniqueRemote remote : this.remotes) {
            if(remote.getId() == statelessTarget.getId()) {
                statelessTarget = remote;
            }
            System.out.println("[REPLICATION] contacting the service with id " + remote.getId());
            results.add(method.invoke(remote, args));
        }
        Object result = null;
        try {
            Consortium c = ConsortiumFactory.getConsortium(ConsortiumType.MAJORITY_CONSORTIUM);
            result = c.resolve(results);
        } catch (UnresolvableConsortiumException e) {
            e.printStackTrace();
        }
        return result;
    }
}
