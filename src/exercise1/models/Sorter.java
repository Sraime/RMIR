package exercise1.models;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Sorter extends Remote {

  public List<String> sort(List<String> list) throws RemoteException;

  public List<String> reverseSort(List<String> list) throws RemoteException;

}
