package app;

public class AgeRange {

    private final int id;
    private final int minAge;
    private final int maxAge;
    private final int description;

    public AgeRange(int id, int minAge, int maxAge, int description) {
        this.id = id;
        this.minAge = minAge;
        this.maxAge = maxAge;
        this.description = description;
    }

    public int getId() { return id; }
    public int getDescription() { return description; }
    public int getMaxAge() { return maxAge; }
    public int getMinAge() { return minAge; }
}
