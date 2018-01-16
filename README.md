Gitlab : https://gitlab.com/Sobraz/RMI

## INTRODUCTION

This framework handles the replication of objects on different servers.


It implements the 3 types of replications policies :
- active
- half active
- passive

With this framework you can use different load balancing. 

Only the round robin is currently implemented.


The architecture is fully maintainable and make the addition of new load balancing, consorcium and replications policies.


You just have to create new factories.

## HOW TO USE THE FRAMEWORK

### Registry

To run the program a registry is needed to allow the servers to register on it.
The clients can contact this registry to access the objects remotely the same way you access local object.

To run the registry you have to use the class GlobalRegistry, like the following exemple:

```
public synchronized static void main(String[] args) throws RemoteException, AlreadyBoundException, InterruptedException {
    GlobalRegistry.loadReagistry();
    MainRegistry.class.wait();
}
```

###Servicies

Servicies available remotely have to implement the UniqueRemote interface.

This interface aims to define actions that can be applied on the remote object. 

Exemple where the ressource is a car :  

```
@Replicated(key = "Car", type = ReplicationType.ACTIVE)
@LoadBalanced(policy = BalancerType.ROUND_ROBIN_BALANCER)
public interface Car extends UniqueRemote {

  String getEngine() throws RemoteException;

  String getDestination() throws RemoteException;

  @Stateful
  void changeEngine(String engine) throws RemoteException;

  @Stateful
  void driveTo(String destination) throws RemoteException;
}
```

##### @Replicated

This annotation is COMPULSORY for every remotes.
It indicates that the object is replicated and its register key.
- key (compulsory) : definition of the register key le Registry
- type (compulsory) : definition of the replication policy

##### @LoadBalanced

This annotation is FACULTATIVE.
It indicates that the registry has to apply a load-balancing policy on this resource.
- policy (facultative) : Choice of the replication policy
         possible values : 
         * ROUND_ROBIN_BALANCER : ressources are distributed cyclically
         * FIRST_INSTANCE_BALANCER (default) : the first available resource is returned
         
#### @Stateful

The goal of this annotation is to define methods which modify the state of the object.
It's COMPULSORY to ensure the replication.

Then you have to create real classes which will be registered.
To continue with the example of the car, we implement a TaxiCar

```
public class TaxiCar extends UUIDUniqueRemote implements Car, Serializable {

    private String engine = "V6";
    private String destination = "Villejuif";

    @Override
    public String getEngine() {
        return this.engine;
    }

    @Override
    public String getDestination() {
        return this.destination;
    }

    @Override
    public void changeEngine(String engine) {
        this.engine = engine;
    }

    @Override
    public void driveTo(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Car " + Thread.currentThread();
    }

    @Override
    public Class<? extends UniqueRemote> getType() {
        return Car.class;
    }

}
```

#### getType()

This function has to be sent to the interface on the associated server.
In the previous example it's the Car interface.

### Servers

Once the servicies are defined, instancies have to be launched on the servers to save it on the registry.
The class ThreadedRemote allows to launch instances of servers : 
```
public static void main(String[] args){
        try {
            ServerRunner.runRemoteServer(new SimpleSorter());
            ServerRunner.runRemoteServer(new TaxiCar());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
```

### Client

The acces to remote resources is really easy.
You only have to retrieve it from the registry via the GlobalRegistry, the retrieve the resource using its key (defined in the interface).

```
public class MainCarClient {

  private static String SERVICE_NAME = "Car";
  private static String SERVICE_HOST = "localhost";

  public static void main(String[] args) throws Exception {

    // locate the registry that runs on the remote object's server
    ReplicationRegistry registry = GlobalRegistry.getRegistry(SERVICE_HOST);

    // retrieve the stub of the remote object by its name
    Car car = (Car) registry.lookup(SERVICE_NAME);
    
    System.out.println("client: car engine is " + car.getEngine());

  }

}
```

### Contact

If you have question on this project, please contact us on robin.collas.efrei@gmail.com.