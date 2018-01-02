package project.Registry;

import project.MainRegistry;
import project.Registry.balancer.Balancer;
import project.Registry.balancer.BalancerFactory;
import project.Registry.balancer.BalancerType;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class GlobaleRegistry implements ReplicationRegistry {
    private Hashtable<String, Balancer> bindings;
    private BalancerType tbalancer;

    public GlobaleRegistry(){
        this(BalancerType.ROUND_ROBIN_BALANCER);
    }

    public GlobaleRegistry(BalancerType tbalancer){
        this.bindings = new Hashtable<String, Balancer>(101);
        this.tbalancer = tbalancer;
    }

    @Override
    public Remote lookup(String key) throws NotBoundException, RemoteException {
        synchronized (this.bindings) {
            Balancer b = (Balancer) this.bindings.get(key);
            if (b == null) {
                throw new NotBoundException(key);
            } else {
                UniqueRemote ur = b.getNext();
                System.out.println("lookup remote object "+ur.getId()+" from key "+key);
                return ur.getPayload();
            }
        }
    }

    @Override
    public void bind(String key, Remote obj) throws RemoteException, AlreadyBoundException, AccessException {
        UniqueRemote ur = (UniqueRemote) obj;
        System.out.println("binding object "+ur.getId()+" with key "+key);
        synchronized (this.bindings) {
            Balancer b = this.bindings.get(key);
            if (b == null) {
                b = BalancerFactory.getBalancer(this.tbalancer);
            } else {
                if(b.containRessouce(ur.getId()))
                    throw new AlreadyBoundException(key);
            }
            b.addRessource(ur);
            this.bindings.put(key, b);
        }
    }

    @Override
    public void unbind(String key) throws RemoteException, NotBoundException, AccessException {
        unbindRemote(key, null);
    }

    @Override
    public void unbindRemote(String key, String id) throws RemoteException, NotBoundException, AccessException {
        System.out.println("unbindind "+(id == null ? "" : "object " + id + " ") + "with key " + key);
        synchronized (this.bindings) {
            Balancer b = (Balancer) this.bindings.get(key);
            if (b == null) {
                throw new NotBoundException(key);
            } else {
                if(id == null)
                    b = BalancerFactory.getBalancer(this.tbalancer);
                else{
                    try {
                        b.removeRessource(id);
                    }catch (NotBoundException e){}
                }
            }
            throw new NotBoundException(key);
        }
    }

    @Override
    public void rebind(String key, Remote obj) throws RemoteException, AccessException {
        try{
            unbindRemote(key, ((UniqueRemote) obj).getId());
        }catch (Exception e){}
        try {
            bind(key, obj);
        } catch (AlreadyBoundException e) {}
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
        Registry gr = LocateRegistry.getRegistry(host, MainRegistry.R_PORT);
        return (ReplicationRegistry) gr.lookup(MainRegistry.GR_KEY);
    }
}