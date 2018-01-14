package project.registry.replication;

import project.registry.UniqueRemote;
import project.registry.replication.ReplicationPolicyInterface;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class PassivePolicy implements ReplicationPolicyInterface {

    private UniqueRemote statelessTarget;
    private List<UniqueRemote> remotes;

    private UniqueRemote statelessTarget;
    private List<UniqueRemote> remotes;

    PassivePolicy(UniqueRemote statelessTarget, List<UniqueRemote> remotes) {
        this.statelessTarget = statelessTarget;
        this.remotes = remotes;
    }

    @Override
    public void applyPolicy(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        this.applyStateless(method, args);
    }

    @Override
    public void applyStateless(Method method, Object[] args) {
        method.invoke(statelessTarget, args);
    }

    @Override
    public void applyStateful(Method method, Object[] args) {
        this.applyStateless(method, args);
    }
}
