package project.registry.remote;

import project.registry.UniqueRemote;
import project.registry.replication.ReplicationPolicy;
import project.registry.replication.ReplicationType;
import project.registry.replication.ReplicattionPolicyRequiredException;
import project.registry.replication.Stateful;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public class RemoteHandler implements InvocationHandler, Remote, Serializable {

    private final UniqueRemote statelessTarget;
    private final List<UniqueRemote> remotes;
    private final ReplicationType replicationPolicy;

    public RemoteHandler(UniqueRemote statelessTarget, List<UniqueRemote> remotes) throws ReplicattionPolicyRequiredException, RemoteException {
        this.remotes = remotes;
        this.statelessTarget = statelessTarget;
        TypedRemoteInterface tri = ((TypedRemoteInterface)statelessTarget.getPayload());
        if(!tri.getType().isAnnotationPresent(ReplicationPolicy.class) && remotes != null)
            throw new ReplicattionPolicyRequiredException();
        ReplicationPolicy a = (ReplicationPolicy) tri.getType().getAnnotation(ReplicationPolicy.class);
       this.replicationPolicy =  remotes == null ? null : a.type();
    }

    public RemoteHandler(UniqueRemote statelessTarget) throws ReplicattionPolicyRequiredException, RemoteException {
        this(statelessTarget, null);
    }

    public Object invoke(Object proxy, Method method, Object[] args)
            throws IllegalAccessException, IllegalArgumentException,
            InvocationTargetException {
        System.out.println("BEFORE");

        if((replicationPolicy == ReplicationType.ACTIVE
                || replicationPolicy == ReplicationType.HALF_ACTIVE) && method.isAnnotationPresent(Stateful.class)){
            System.out.println("apply statefull policy");
        } else {
            System.out.println("apply stateless policy");
        }
        try {
            method.invoke((statelessTarget.getPayload()), args);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        System.out.println("AFTER");
        return null;
    }
}
