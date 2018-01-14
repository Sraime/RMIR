package project.registry.remote;

import project.registry.UniqueRemote;
import project.registry.remote.TypedRemoteInterface;

import java.rmi.RemoteException;
import java.util.UUID;

public class RemoteTypedPacket implements UniqueRemote {

    private final String ID;
    private TypedRemoteInterface payload;

    public RemoteTypedPacket(TypedRemoteInterface payload) {
        this.ID = UUID.randomUUID() + "";
        this.payload = payload;
    }

    @Override
    public String getId() throws RemoteException {
        return ID;
    }

    @Override
    public TypedRemoteInterface getPayload() throws RemoteException {
        return payload;
    }
}
