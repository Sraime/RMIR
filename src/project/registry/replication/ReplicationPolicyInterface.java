package project.registry.replication;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ReplicationPolicyInterface extends Remote, Serializable{

    Object applyPolicy(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException, RemoteException;

    Object applyStateless(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException, RemoteException;

    Object applyStateful(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException, RemoteException;
}
