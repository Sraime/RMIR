package project_exemple.servers.sorter;

import project.registry.balancer.BalancerType;
import project.registry.balancer.LoadBalanced;
import project.registry.replication.ReplicationPolicy;
import project.registry.replication.ReplicationType;
import project.service.RemoteRessource;

import java.rmi.RemoteException;
import java.util.List;

@LoadBalanced(policy = BalancerType.ROUND_ROBIN_BALANCER)
@ReplicationPolicy(type = ReplicationType.PASSIVE)
public interface Sorter extends RemoteRessource {

  public List<String> sort(List<String> list) throws RemoteException;

  public List<String> reverseSort(List<String> list) throws RemoteException;

}