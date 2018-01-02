package project;

import project.Server.RemoteServer;
import project.Server.SorterService;

import java.util.Scanner;

public class MainServers {

    public static void main(String[] args){
        try {
            RemoteServer rs = new SorterService();
            rs.start();

            Scanner pauser = new Scanner (System.in);
            pauser.nextLine();

            rs.interrupt();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
