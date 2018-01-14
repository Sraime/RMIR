package project.registry.remote;

import project.registry.replication.ReplicationPolicyInterface;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class RemoteHandler implements InvocationHandler, Remote, Serializable {

    private ReplicationPolicyInterface policy;

    public RemoteHandler(ReplicationPolicyInterface policy) {
        this.policy = policy;
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws IllegalArgumentException {
        try {
            return policy.applyPolicy(method, args);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
