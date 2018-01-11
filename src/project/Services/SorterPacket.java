package project.Services;

import project.Registry.remote.TypedRemoteInterface;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class SorterPacket implements Sorter, Serializable, TypedRemoteInterface {

    @Override
    public List<String> sort(List<String> list) {


        System.out.println(this + ": receveid " + list);

        Collections.sort(list);

        System.out.println(this + ": returning " + list);
        return list;
    }

    @Override
    public List<String> reverseSort(List<String> list) {

        System.out.println(this + ": receveid " + list);

        Collections.sort(list);
        Collections.reverse(list);

        System.out.println(this + ": returning " + list);
        return list;
    }

    @Override
    public String toString() {
        return "SimpleSorter " + Thread.currentThread();
    }

    @Override
    public Class<? extends RemoteRessource> getType() {
        return Sorter.class;
    }
}
