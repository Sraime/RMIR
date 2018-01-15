package project_exemple.servers.sorter;

import project.Service.UUIDUniqueRemote;
import project.registry.UniqueRemote;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class SimpleSorter extends UUIDUniqueRemote implements Sorter, Serializable {

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
    public Class<? extends UniqueRemote> getType() {
        return Sorter.class;
    }

}
