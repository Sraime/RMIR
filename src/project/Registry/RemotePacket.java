package project.Registry;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.UUID;

public class RemotePacket implements UniqueRemote {

    private final String ID;
    private Remote payload;

    public RemotePacket(Remote payload){
        this.ID = UUID.randomUUID()+"";
        this.payload = payload;
    }

    @Override
    public String getId() {
        return ID;
    }

    @Override
    public Remote getPayload() throws RemoteException {
        return payload;
    }

}
