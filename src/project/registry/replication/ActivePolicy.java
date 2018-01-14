package project.registry.replication;

import project.registry.UniqueRemote;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class ActivePolicy implements ReplicationPolicyInterface{

    private UniqueRemote statelessTarget;
    private List<UniqueRemote> remotes;

    ActivePolicy(UniqueRemote statelessTarget, List<UniqueRemote> remotes){
        this.statelessTarget = statelessTarget;
        this.remotes = remotes;
    }
    @Override
    public void applyPolicy(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        method.invoke(remotes.get(0),args);
    }
}
