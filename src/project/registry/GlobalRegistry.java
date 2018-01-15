package project.registry;

import project.registry.balancer.Balancer;
import project.registry.balancer.BalancerFactory;
import project.registry.balancer.BalancerType;
import project.registry.balancer.LoadBalanced;
import project.registry.remote.RemoteHandler;
import project.registry.replication.Replicated;
import project.registry.replication.ReplicationPolicyFactory;

import java.lang.reflect.Proxy;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class GlobalRegistry implements ReplicationRegistry {

    public static final int R_PORT = 1099;
    public static final String GR_KEY = "GR";
    private Hashtable<String, Balancer> bindings;

    private GlobalRegistry() {
        this.bindings = new Hashtable<String, Balancer>(101);
    }

    public static ReplicationRegistry loadReagistry() throws RemoteException, AlreadyBoundException {
        ReplicationRegistry globalRegistry = new GlobalRegistry();
        System.out.println("globale registry created");
        Registry registry = LocateRegistry.createRegistry(R_PORT);
        System.out.println("registry listenning on port " + R_PORT);
        globalRegistry = (ReplicationRegistry) UnicastRemoteObject.exportObject(globalRegistry, 0);
        registry.bind(GR_KEY, globalRegistry);
        System.out.println("registry binded with key " + GR_KEY);
        System.out.println("registries are ready to be used ");
        return globalRegistry;
    }

    @Override
    public Remote lookup(String key) throws NotBoundException, RemoteException {
        synchronized (this.bindings) {
            Balancer b = this.bindings.get(key);
            if (b == null) {
                throw new NotBoundException(key);
            } else {
                UniqueRemote ur = b.getNext();
                Replicated rp = ur.getType().getAnnotation(Replicated.class);
                RemoteHandler handler = new RemoteHandler(ReplicationPolicyFactory.getPolicy(rp.type(), ur, b.getRessources()));
                System.out.println("lookup remote object " + ur.getId() + " from key " + key);
                return (Remote) Proxy.newProxyInstance(ur.getType().getClassLoader(),
                        new Class[]{ur.getType()},
                        handler);
            }
        }
    }

    @Override
    public void bind(String key, Remote obj) throws RemoteException, AlreadyBoundException {
        UniqueRemote ur = (UniqueRemote) obj;
        System.out.println("binding object " + ur.getId() + " with key " + key);
        synchronized (this.bindings) {
            Balancer b = this.bindings.get(key);
            if (b == null) {
                b = BalancerFactory.getBalancer(getBalancerType(ur));
            } else {
                if (b.containRessouce(ur.getId()))
                    throw new AlreadyBoundException(key);
            }
            b.addRessource(ur);
            this.bindings.put(key, b);
        }
    }

    @Override
    public void unbind(String key) throws RemoteException, NotBoundException {
        unbindRemote(key, null);
    }

    @Override
    public void unbindRemote(String key, String id) throws RemoteException, NotBoundException {
        System.out.println("unbindind " + (id == null ? "" : "object " + id + " ") + "with key " + key);
        synchronized (this.bindings) {
            Balancer b = (Balancer) this.bindings.get(key);
            if (b == null) {
                throw new NotBoundException(key);
            } else {
                if (id == null)
                    b = null;
                else {
                    try {
                        b.removeRessource(id);
                    } catch (NotBoundException e) {
                    }
                }
            }
            throw new NotBoundException(key);
        }
    }

    @Override
    public void rebind(String key, Remote obj) throws RemoteException {
        try {
            unbindRemote(key, ((UniqueRemote) obj).getId());
        } catch (Exception e) {
        }
        try {
            bind(key, obj);
        } catch (AlreadyBoundException e) {
        }
    }

    @Override
    public String[] list() {
        synchronized (this.bindings) {
            int size = this.bindings.size();
            String[] str = new String[size];
            Enumeration keys = this.bindings.keys();

            while (true) {
                --size;
                if (size < 0) {
                    return str;
                }

                str[size] = (String) keys.nextElement();
            }
        }
    }

    public static ReplicationRegistry getRegistry(String host) throws RemoteException, NotBoundException {
        Registry gr = LocateRegistry.getRegistry(host, R_PORT);
        return (ReplicationRegistry) gr.lookup(GR_KEY);
    }

    private BalancerType getBalancerType(UniqueRemote ur) throws RemoteException {
        LoadBalanced lb = ur.getType().getAnnotation(LoadBalanced.class);
        return lb.policy();
    }


}