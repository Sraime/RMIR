package exercise1.models;
import java.io.Serializable;

public class CarPacket implements Car {

    private String engine = "V6";

    @Override
    public String getEngine() {
        return this.engine;
    }

    @Override
    public void changeEngine(String engine) {
        this.engine = engine;
    }

    @Override
    public String toString() {
        return "Car " + Thread.currentThread();
    }
}
