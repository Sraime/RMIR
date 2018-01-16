package project.registry.balancer;

public class BalancerFactory {

    public static Balancer getBalancer(BalancerType t) {
        switch (t) {
            case ROUND_ROBIN_BALANCER:
                return new RoundRobinBalancer();
            case FIRST_INSTANCE_BALANCER:
                return new FirstInstanceBalancer();
            default:
                return null;
        }
    }
}
