package project.registry.remote;

import project.registry.replication.ReplicationPolicy;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;

public class RemoteHandler implements InvocationHandler, Remote, Serializable {

    private ReplicationPolicy policy;

    public RemoteHandler(ReplicationPolicy policy) {
        this.policy = policy;
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws IllegalArgumentException {
        try {
            System.out.println("[PROXY] Requesting for the " + method.getName() + " method");
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
