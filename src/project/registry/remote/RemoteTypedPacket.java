package project.registry.remote;

import project.registry.UniqueRemote;

import java.rmi.RemoteException;
import java.util.UUID;

public class RemoteTypedPacket implements UniqueRemote {

    private final String ID;
    private TypedRemote payload;

    public RemoteTypedPacket(TypedRemote payload) {
        this.ID = UUID.randomUUID() + "";
        this.payload = payload;
    }

    @Override
    public String getId() throws RemoteException {
        return ID;
    }

    @Override
    public TypedRemote getPayload() throws RemoteException {
        return payload;
    }
}
