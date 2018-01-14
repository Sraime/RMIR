package project.registry.remote;

import project.service.RemoteRessource;

import java.rmi.Remote;

public interface TypedRemoteInterface extends Remote{
    public Class<? extends RemoteRessource> getType();
}