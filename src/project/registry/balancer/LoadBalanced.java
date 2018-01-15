package project.registry.balancer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LoadBalanced {
    BalancerType policy() default BalancerType.FIRST_INSTANCE_BALANCER;
}
