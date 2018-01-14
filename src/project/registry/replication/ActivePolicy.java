package project.registry.replication;

import project.registry.UniqueRemote;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

public class ActivePolicy implements ReplicationPolicyInterface {

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
        System.out.println("[POLICY] applying the Active replication in stateless mode");
        System.out.println("[POLICY] contacting the service with id " + statelessTarget.getId());
        return method.invoke(statelessTarget.getPayload(), args);
    }

    @Override
    public Object applyStateful(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException, RemoteException {
        System.out.println("[POLICY] applying the Active replication in stateful mode");
        LinkedList<Object> results = new LinkedList<Object>();
        for (UniqueRemote remote : this.remotes) {
            System.out.println("[POLICY] contacting the service with id " + remote.getId());
            results.add(method.invoke(remote.getPayload(), args));
        }
        // CONCENSUS TODO
        return results.get(0);
    }
}
