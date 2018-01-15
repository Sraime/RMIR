package project.registry.replication.consortium;

public abstract class ConsortiumFactory {

    public static Consortium getConsortium(ConsortiumType ct) {
        switch (ct) {
            case MAJORITY_CONSORTIUM:
                return new MajorityConsortium();
        }
        return null;
    }
}
