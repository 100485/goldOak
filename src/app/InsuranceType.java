package app;

public class InsuranceType {
    private final int id;
    private final String description; // eg: inpatient, outpatient

    public InsuranceType(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() { return id; }
    public String getDescription() { return description; }
}
