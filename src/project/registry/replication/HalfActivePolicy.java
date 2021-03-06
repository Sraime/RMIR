package project.registry.replication;

import project.registry.UniqueRemote;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.List;

public class HalfActivePolicy implements ReplicationPolicy {

    private UniqueRemote statelessTarget;
    private List<UniqueRemote> remotes;

    HalfActivePolicy(UniqueRemote statelessTarget, List<UniqueRemote> remotes) {
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
        System.out.println("[REPLICATION] applying the HalfActive replication in stateless mode");
        System.out.println("[REPLICATION] contacting the service with id " + statelessTarget.getId());
        return method.invoke(statelessTarget, args);
    }

    @Override
    public Object applyStateful(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException, RemoteException {
        System.out.println("[REPLICATION] applying the HalfActive replication in stateful mode");
        Object result = null;
        for (int i = 0; i < remotes.size(); i++) {
            System.out.println("[REPLICATION] contacting the service with id " + remotes.get(i).getId());
            if (i == 0)
                result = method.invoke(remotes.get(i), args);
            method.invoke(remotes.get(i), args);
        }
        return result;
    }
}
