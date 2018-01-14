package project.registry.replication;

import project.registry.UniqueRemote;
import project.registry.replication.ReplicationPolicyInterface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.RemoteException;
import java.util.List;

public class PassivePolicy implements ReplicationPolicyInterface {

    public static final int LEAD_INDEX = 0;

    private UniqueRemote lead;

    PassivePolicy(UniqueRemote lead) {
        this.lead = lead;
    }

    @Override
    public Object applyPolicy(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException, RemoteException {
        return this.applyStateless(method, args);
    }

    @Override
    public Object applyStateless(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException, RemoteException {
        System.out.println("[POLICY] applying the Passive replication");
        System.out.println("[POLICY] contacting the service with id " + lead.getId());
        return method.invoke(lead.getPayload(), args);
    }

    @Override
    public Object applyStateful(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException, RemoteException {
        return this.applyStateless(method, args);
    }
}
