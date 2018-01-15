package project.registry.replication.consortium;

import java.util.List;

public interface Consortium {

    public Object resolve(List<Object> objects) throws UnresolvableConsortiumException;
}
