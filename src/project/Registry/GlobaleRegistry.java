package project.Registry;

import java.io.FilePermission;
import java.net.InetAddress;
import java.net.SocketPermission;
import java.net.URL;
import java.net.URLClassLoader;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ObjID;
import java.rmi.server.RMIClientSocketFactory;
import java.rmi.server.RMIServerSocketFactory;
import java.rmi.server.RemoteServer;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;
import java.text.MessageFormat;
import java.util.*;

import project.MainRegistry;
import sun.misc.URLClassPath;
import sun.rmi.server.LoaderHandler;
import sun.rmi.server.UnicastServerRef;
import sun.rmi.server.UnicastServerRef2;
import sun.rmi.transport.LiveRef;

public class GlobaleRegistry extends RemoteServer implements Registry {
    private static final long serialVersionUID = 4666870661827494597L;
    private Hashtable<String,Remote> bindings;
    private static Hashtable<InetAddress, InetAddress> allowedAccessCache = new Hashtable(3);
    private static GlobaleRegistry registry;
    private static ObjID id = new ObjID(0);
    private static ResourceBundle resources = null;
    private static final String REGISTRY_FILTER_PROPNAME = "sun.rmi.registry.registryFilter";
    private static final int REGISTRY_MAX_DEPTH = 20;
    private static final int REGISTRY_MAX_ARRAY_SIZE = 10000;

    public GlobaleRegistry(final int var1, final RMIClientSocketFactory var2, final RMIServerSocketFactory var3) throws RemoteException {
        this.bindings = new Hashtable(101);
        if (var1 == 1099 && System.getSecurityManager() != null) {
            try {
                AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
                    public Void run() throws RemoteException {
                        LiveRef var1x = new LiveRef(GlobaleRegistry.id, var1, var2, var3);
                        GlobaleRegistry.this.setup(new UnicastServerRef2(var1x));
                        return null;
                    }
                }, (AccessControlContext)null, new SocketPermission("localhost:" + var1, "listen,accept"));
            } catch (PrivilegedActionException var6) {
                throw (RemoteException)var6.getException();
            }
        } else {
            LiveRef var5 = new LiveRef(id, var1, var2, var3);
            this.setup(new UnicastServerRef2(var5));
        }

    }

    public GlobaleRegistry(final int var1) throws RemoteException {
        this.bindings = new Hashtable(101);
        if (var1 == 1099 && System.getSecurityManager() != null) {
            try {
                AccessController.doPrivileged(new PrivilegedExceptionAction<Void>() {
                    public Void run() throws RemoteException {
                        LiveRef var1x = new LiveRef(GlobaleRegistry.id, var1);
                        GlobaleRegistry.this.setup(new UnicastServerRef(var1x));
                        return null;
                    }
                }, (AccessControlContext)null, new SocketPermission("localhost:" + var1, "listen,accept"));
            } catch (PrivilegedActionException var3) {
                throw (RemoteException)var3.getException();
            }
        } else {
            LiveRef var2 = new LiveRef(id, var1);
            this.setup(new UnicastServerRef(var2));
        }

    }

    private void setup(UnicastServerRef var1) throws RemoteException {
        this.ref = var1;
        var1.exportObject(this, (Object)null, true);
    }

    public Remote lookup(String var1) throws RemoteException, NotBoundException {
        Hashtable var2 = this.bindings;
        synchronized(this.bindings) {
            Remote var3 = (Remote)this.bindings.get(var1);
            if (var3 == null) {
                throw new NotBoundException(var1);
            } else {
                return var3;
            }
        }
    }

    public void bind(String var1, Remote var2) throws RemoteException, AlreadyBoundException, AccessException {
        Hashtable var3 = this.bindings;
        synchronized(this.bindings) {
            Remote var4 = (Remote)this.bindings.get(var1);
            if (var4 != null) {
                throw new AlreadyBoundException(var1);
            } else {
                this.bindings.put(var1, var2);
            }
        }
    }

    public void unbind(String var1) throws RemoteException, NotBoundException, AccessException {
        Hashtable var2 = this.bindings;
        synchronized(this.bindings) {
            Remote var3 = (Remote)this.bindings.get(var1);
            if (var3 == null) {
                throw new NotBoundException(var1);
            } else {
                this.bindings.remove(var1);
            }
        }
    }

    public void rebind(String var1, Remote var2) throws RemoteException, AccessException {
        this.bindings.put(var1, var2);
    }

    public String[] list() throws RemoteException {
        Hashtable var2 = this.bindings;
        synchronized(this.bindings) {
            int var3 = this.bindings.size();
            String[] var1 = new String[var3];
            Enumeration var4 = this.bindings.keys();

            while(true) {
                --var3;
                if (var3 < 0) {
                    return var1;
                }

                var1[var3] = (String)var4.nextElement();
            }
        }
    }

    public static ObjID getID() {
        return id;
    }

    private static String getTextResource(String var0) {
        if (resources == null) {
            try {
                resources = ResourceBundle.getBundle("sun.rmi.registry.resources.rmiregistry");
            } catch (MissingResourceException var4) {
                ;
            }

            if (resources == null) {
                return "[missing resource file: " + var0 + "]";
            }
        }

        String var1 = null;

        try {
            var1 = resources.getString(var0);
        } catch (MissingResourceException var3) {
            ;
        }

        return var1 == null ? "[missing resource: " + var0 + "]" : var1;
    }

    public static void main(String[] var0) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }

        try {
            String var1 = System.getProperty("env.class.path");
            if (var1 == null) {
                var1 = ".";
            }

            URL[] var2 = URLClassPath.pathToURLs(var1);
            URLClassLoader var3 = new URLClassLoader(var2);
            LoaderHandler.registerCodebaseLoader(var3);
            Thread.currentThread().setContextClassLoader(var3);
            final int var4 = var0.length >= 1 ? Integer.parseInt(var0[0]) : 1099;

            try {
                registry = (GlobaleRegistry)AccessController.doPrivileged(new PrivilegedExceptionAction<GlobaleRegistry>() {
                    public GlobaleRegistry run() throws RemoteException {
                        return new GlobaleRegistry(var4);
                    }
                }, getAccessControlContext(var4));
            } catch (PrivilegedActionException var6) {
                throw (RemoteException)var6.getException();
            }

            while(true) {
                while(true) {
                    try {
                        Thread.sleep(9223372036854775807L);
                    } catch (InterruptedException var7) {
                        ;
                    }
                }
            }
        } catch (NumberFormatException var8) {
            System.err.println(MessageFormat.format(getTextResource("rmiregistry.port.badnumber"), var0[0]));
            System.err.println(MessageFormat.format(getTextResource("rmiregistry.usage"), "rmiregistry"));
        } catch (Exception var9) {
            var9.printStackTrace();
        }

        System.exit(1);
    }

    private static AccessControlContext getAccessControlContext(int var0) {
        PermissionCollection var1 = (PermissionCollection)AccessController.doPrivileged(new PrivilegedAction<PermissionCollection>() {
            public PermissionCollection run() {
                CodeSource var1 = new CodeSource((URL)null, (Certificate[])null);
                Policy var2 = Policy.getPolicy();
                return (PermissionCollection)(var2 != null ? var2.getPermissions(var1) : new Permissions());
            }
        });
        var1.add(new SocketPermission("*", "connect,accept"));
        var1.add(new SocketPermission("localhost:" + var0, "listen,accept"));
        var1.add(new RuntimePermission("accessClassInPackage.sun.jvmstat.*"));
        var1.add(new RuntimePermission("accessClassInPackage.sun.jvm.hotspot.*"));
        var1.add(new FilePermission("<<ALL FILES>>", "read"));
        ProtectionDomain var2 = new ProtectionDomain(new CodeSource((URL)null, (Certificate[])null), var1);
        return new AccessControlContext(new ProtectionDomain[]{var2});
    }

    public static Registry getRegistry(String host) throws RemoteException, NotBoundException {
        Registry gr = LocateRegistry.getRegistry(host, MainRegistry.R_PORT);
        return (Registry) gr.lookup(MainRegistry.GR_KEY);
    }
}
