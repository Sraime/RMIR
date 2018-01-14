package project.registry.replication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface ReplicationPolicyInterface {

    public void applyPolicy(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException;
}
