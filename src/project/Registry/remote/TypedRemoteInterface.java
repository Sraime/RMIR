package project.Registry.remote;

import project.Services.RemoteRessource;

import java.rmi.Remote;

public interface TypedRemoteInterface extends Remote{
    public Class<? extends RemoteRessource> getType();
}
