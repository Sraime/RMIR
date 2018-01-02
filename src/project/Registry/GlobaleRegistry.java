package project.Registry;

import project.MainRegistry;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class GlobaleRegistry implements ReplicationRegistry {
    private Hashtable<String, List<UniqueRemote>> bindings;

    public GlobaleRegistry(){
        this.bindings = new Hashtable<String, List<UniqueRemote>>(101);
    }

    @Override
    public Remote lookup(String key) throws NotBoundException, RemoteException {
        System.out.println("lookup from key "+key);
        synchronized (this.bindings) {
            List<UniqueRemote> list = (List<UniqueRemote>) this.bindings.get(key);
            if (list == null) {
                throw new NotBoundException(key);
            } else {
                return list.get(list.size()-1).getPayload();
            }
        }
    }

    @Override
    public void bind(String key, Remote obj) throws RemoteException, AlreadyBoundException, AccessException {
        System.out.println("binding object "+((UniqueRemote) obj).getId()+" with key "+key);
        synchronized (this.bindings) {
            List<UniqueRemote> list = (List<UniqueRemote>) this.bindings.get(key);
            if (list == null) {
                list = new LinkedList<UniqueRemote>();
            } else {
                for(UniqueRemote ro : list)
                    if(ro.getId().equals(((UniqueRemote) obj).getId()))
                        throw new AlreadyBoundException(key);
            }
            list.add((UniqueRemote) obj);
            this.bindings.put(key, list);
        }
    }

    @Override
    public void unbind(String key) throws RemoteException, NotBoundException, AccessException {
        unbindRemote(key, null);
    }

    @Override
    public void unbindRemote(String key, String id) throws RemoteException, NotBoundException, AccessException {
        System.out.println("unbindind key "+key);
        synchronized (this.bindings) {
            List<UniqueRemote> list = (List<UniqueRemote>) this.bindings.get(key);
            if (list == null) {
                throw new NotBoundException(key);
            } else {
                if(id == null)
                    list = new LinkedList<UniqueRemote>();
                else{
                    for (UniqueRemote ro : list) {
                        if (ro.getId().equals(id)) {
                            list.remove(ro);
                            return;
                        }
                    }
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