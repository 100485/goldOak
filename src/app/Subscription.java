package app;

public class Subscription {

    private final int id;
    private final String name;
    private final double limit;

    public Subscription(int id, String name, double limit) {
        this.id = id;
        this.name = name;
        this.limit = limit;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getLimit() { return limit; }
}
