package project.registry.replication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface ReplicationPolicyInterface {

    void applyPolicy(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException;

    void applyStateless(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException;

    void applyStateful(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException;
}
