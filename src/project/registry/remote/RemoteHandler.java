package project.registry.remote;

import project.registry.replication.ReplicationPolicyInterface;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.rmi.Remote;

public class RemoteHandler implements InvocationHandler, Remote, Serializable {

    private ReplicationPolicyInterface policy;

    public RemoteHandler(ReplicationPolicyInterface policy) {
        this.policy = policy;
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws IllegalArgumentException {
        System.out.println("BEFORE");
        policy.applyPolicy(method, args);
        System.out.println("AFTER");
        return null;
    }
}
