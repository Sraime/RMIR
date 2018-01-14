package project.registry.balancer;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface LoadBalanced {
    BalancerType policy() default BalancerType.FIRST_INSTANCE_BALANCER;
}
