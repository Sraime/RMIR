package project.Services;

import project.Registry.balancer.BalancerType;
import project.Registry.balancer.LoadBalanced;
import project.Registry.replication.ReplicationPolicy;
import project.Registry.replication.ReplicationType;

import java.rmi.RemoteException;
import java.util.List;

@LoadBalanced(policy = BalancerType.ROUND_ROBIN_BALANCER)
@ReplicationPolicy(type = ReplicationType.PASSIVE)
public interface Sorter extends RemoteRessource {

  public List<String> sort(List<String> list) throws RemoteException;

  public List<String> reverseSort(List<String> list) throws RemoteException;

}
