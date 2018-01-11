package project.Registry.replication;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ReplicationPolicy {
    ReplicationType type() default ReplicationType.ACTIVE;
}
