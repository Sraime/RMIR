package project.registry.replication.consortium;

import javafx.util.Pair;

import java.util.List;

public class MajorityConsortium implements Consortium {
    @Override
    public Object resolve(List<Object> objects){
        System.out.println("[CONSORTIUM] resolving return data");
        Pair<Integer,Object> major = new Pair<Integer,Object>(0,null);
        for (int i = 0, nb = 1; i < objects.size(); i++, nb++){
            if(i == objects.size()-1 || (i != 0 && ! objects.get(i - 1).equals(objects.get(i)))) {
                if(major.getKey() < nb)
                    major = new Pair<Integer,Object>(nb,objects.get(objects.size() == 1 ? 0 : i - 1 ));
                nb = 0;
            }
            nb++;
        }
        return major.getValue();
    }
}
