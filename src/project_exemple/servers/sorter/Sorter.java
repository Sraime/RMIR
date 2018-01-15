package project_exemple.servers.sorter;

import project.registry.UniqueRemote;
import project.registry.balancer.BalancerType;
import project.registry.balancer.LoadBalanced;
import project.registry.replication.Replicated;
import project.registry.replication.ReplicationType;
import project.registry.replication.Stateful;

import java.rmi.RemoteException;
import java.util.List;

@LoadBalanced(policy = BalancerType.ROUND_ROBIN_BALANCER)
@Replicated(key = "Sorter", type = ReplicationType.ACTIVE)
public interface Sorter extends UniqueRemote
{

  public List<String> sort(List<String> list) throws RemoteException;

  @Stateful
  public List<String> reverseSort(List<String> list) throws RemoteException;

}
