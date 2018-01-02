package project.Registry.balancer;

public class BalancerFactory {

    public static Balancer getBalancer(BalancerType t){
        switch(t){
            case ROUND_ROBIN_BALANCER :
                return new RoundRobinBalancer();
            default :
                return null;
        }
    }
}
