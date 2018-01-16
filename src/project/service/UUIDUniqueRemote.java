package project.service;

import project.registry.UniqueRemote;

import java.util.UUID;

public abstract class UUIDUniqueRemote implements UniqueRemote{
    private String id;

    protected UUIDUniqueRemote(){
        this.id = UUID.randomUUID().toString();
    }

    @Override
    public String getId() {
        return id;
    }
}
