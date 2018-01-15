package project_exemple.servers.car;

import project.registry.UniqueRemote;
import project.registry.balancer.BalancerType;
import project.registry.balancer.LoadBalanced;
import project.registry.replication.Replicated;
import project.registry.replication.ReplicationType;
import project.registry.replication.Stateful;

import java.rmi.RemoteException;

@LoadBalanced(policy = BalancerType.ROUND_ROBIN_BALANCER)
@Replicated(key = "Car", type = ReplicationType.HALF_ACTIVE)
public interface Car extends UniqueRemote {

  String getEngine() throws RemoteException;

  String getDestination() throws RemoteException;

  @Stateful
  void changeEngine(String engine) throws RemoteException;

  @Stateful
  void driveTo(String destination) throws RemoteException;

}
