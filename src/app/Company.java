package app;

public class Company {
    private final int id;
    private final String name;
    private final String details;

    public Company(int id, String name, String details) {
        this.id = id;
        this.name = name;
        this.details = details;
    }

    public int getId() { return this.id; }
    public String getName() { return this.name; }
    public String getDetails() { return this.details; }

    public String[] toStringArray() {
        return new String[] {
                String.valueOf(id),
                name,
                details
        };
    }
}
