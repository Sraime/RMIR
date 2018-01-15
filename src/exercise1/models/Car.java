package exercise1.models;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Car extends Remote {

  public String getEngine() throws RemoteException;

  public void changeEngine(String engine) throws RemoteException;

}
