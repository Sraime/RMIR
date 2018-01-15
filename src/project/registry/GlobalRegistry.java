package project.registry;

import project.registry.balancer.Balancer;
import project.registry.balancer.BalancerFactory;
import project.registry.balancer.BalancerType;
import project.registry.balancer.LoadBalanced;
import project.registry.remote.RemoteHandler;
import project.registry.remote.TypedRemote;
import project.registry.replication.Replicated;
import project.registry.replication.ReplicationPolicyFactory;

import java.lang.reflect.Proxy;
import java.rmi.AccessException;
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
    private Hashtable<String, List<UniqueRemote>> bindings;
    private Hashtable<String, Balancer> balancers;

    private GlobalRegistry() {
        this.bindings = new Hashtable<String, List<UniqueRemote>>(101);
        this.balancers = new Hashtable<String, Balancer>();
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
            Balancer b = this.balancers.get(key);
            if (b == null) {
                throw new NotBoundException(key);
            } else {
                UniqueRemote ur = b.getNext(bindings.get(key));
                TypedRemote tri = (TypedRemote) ur.getPayload();
                Replicated rp = tri.getType().getAnnotation(Replicated.class);
                RemoteHandler handler = new RemoteHandler(ReplicationPolicyFactory.getPolicy(rp.type(), ur, bindings.get(key)));
                System.out.println("lookup remote object " + ur.getId() + " from key " + key);
                return (Remote) Proxy.newProxyInstance(tri.getType().getClassLoader(),
                        new Class[]{tri.getType()},
                        handler);
            }
        }
    }

    @Override
    public void bind(String key, Remote obj) throws RemoteException, AlreadyBoundException, AccessException {
        UniqueRemote ur = (UniqueRemote) obj;
        System.out.println("binding object " + ur.getId() + " with key " + key);
        synchronized (this.bindings) {
            Balancer b = this.balancers.get(key);
            List<UniqueRemote> list = this.bindings.get(key);
            if (b == null) {
                this.balancers.put(key,BalancerFactory.getBalancer(getBalancerType(ur)));
                this.bindings.put(key,list = new LinkedList<UniqueRemote>());
            } else {
                if (ressourceIndex(this.bindings.get(key),((UniqueRemote) obj).getId()) > 0)
                    throw new AlreadyBoundException(key);
            }
            this.bindings.get(key).add(ur);
        }
    }

    private int ressourceIndex(List<UniqueRemote> list, String id) {
        for(int i = 0; i < list.size(); i++) {
            try {
                if (list.get(i).getId().equals(id))
                    return i;
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    @Override
    public void unbind(String key) throws RemoteException, NotBoundException, AccessException {
        unbindRemote(key, null);
    }

    @Override
    public void unbindRemote(String key, String id) throws RemoteException, NotBoundException, AccessException {
        System.out.println("unbindind " + (id == null ? "" : "object " + id + " ") + "with key " + key);
        synchronized (this.bindings) {
            Balancer b = this.balancers.get(key);
            if (b == null) {
                throw new NotBoundException(key);
            } else {
                if (id == null)
                    b = null;
                else {
                    int index = ressourceIndex(this.bindings.get(key),id);
                    if (index < 1)
                        throw new NotBoundException(key);
                    this.bindings.get(key).remove(index);
                }
            }
        }
    }

    @Override
    public Remote newLookup(String key) throws RemoteException {
        synchronized (this.bindings) {
            return this.bindings.get(key).get(0).getPayload();
        }
    }

    @Override
    public void rebind(String key, Remote obj) throws RemoteException, AccessException {
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
    public String[] list() throws RemoteException {
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
        TypedRemote tri = (TypedRemote) ur.getPayload();
        LoadBalanced lb = tri.getType().getAnnotation(LoadBalanced.class);
        return lb.policy();
    }


}