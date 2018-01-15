package project.registry.replication;

import project.registry.replication.ReplicationType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Replicated {
    String key();
    ReplicationType type() default ReplicationType.ACTIVE;
}
