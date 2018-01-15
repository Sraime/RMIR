package project_exemple;

import project.server.RemoteServer;
import project.server.ServerRunner;
import project.server.ThreadedServer;
import project_exemple.servers.sorter.SorterPacket;


import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class MainServers {

    public static void main(String[] args){
        try {
            ServerRunner.runRemoteServer(new SorterPacket());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
