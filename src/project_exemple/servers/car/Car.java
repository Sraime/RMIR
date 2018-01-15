package project_exemple.servers.car;

import project.registry.balancer.BalancerType;
import project.registry.balancer.LoadBalanced;
import project.registry.replication.Replicated;
import project.registry.replication.ReplicationType;
import project.registry.replication.Stateful;
import project.service.RemoteRessource;

import java.rmi.RemoteException;

@LoadBalanced(policy = BalancerType.ROUND_ROBIN_BALANCER)
@Replicated(key = "Car", type = ReplicationType.ACTIVE)
public interface Car extends RemoteRessource {

  public String getEngine() throws RemoteException;

  public String getDestination() throws RemoteException;

  @Stateful
  public void changeEngine(String engine) throws RemoteException;

  @Stateful
  public void driveTo(String destination) throws RemoteException;

}
