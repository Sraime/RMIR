package project.registry.replication;

import project.registry.UniqueRemote;

import java.util.List;

public class ReplicationPolicyFactory {

    public static ReplicationPolicy getPolicy(ReplicationType type, UniqueRemote stateless, List<UniqueRemote> remotes) {
        switch (type) {
            case PASSIVE:
                return new PassivePolicy(remotes.get(PassivePolicy.LEAD_INDEX));
            case ACTIVE:
                return new ActivePolicy(stateless, remotes);
            case HALF_ACTIVE:
                return new HalfActivePolicy(stateless,remotes);
        }
        return null;
    }
}
