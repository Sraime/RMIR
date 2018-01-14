package project.registry.replication;

import project.registry.UniqueRemote;
import project.registry.replication.ReplicationPolicyInterface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class HalfActivePolicy implements ReplicationPolicyInterface {

    private UniqueRemote statelessTarget;
    private List<UniqueRemote> remotes;

    HalfActivePolicy(UniqueRemote statelessTarget, List<UniqueRemote> remotes) {
        this.statelessTarget = statelessTarget;
        this.remotes = remotes;
    }

    @Override
    public void applyPolicy(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        if(!method.isAnnotationPresent(project.registry.replication.Stateful.class)) {
            this.applyStateless(method, args);
        } else {
            this.applyStateful(method, args);
        }
    }

    @Override
    public void applyStateless(Method method, Object[] args) {
        method.invoke(statelessTarget, args);
    }

    @Override
    public void applyStateful(Method method, Object[] args) {
        for (UniqueRemote remote : this.remotes) {
            method.invoke(remote, args);
        }
    }
}
