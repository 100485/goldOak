package app;

public class Price {
    private final int id;
    private int companyId;
    private int insuranceTypeId;
    private int subscriptionId;
    private int ageRangeId;
    private int roleId;
    private int amount;

    public Price(int id, int companyId, int insuranceTypeId, int subscriptionId, int ageRangeId, int roleId, int amount) {
        this.id = id;
        this.companyId = companyId;
        this.insuranceTypeId = insuranceTypeId;
        this.subscriptionId = subscriptionId;
        this.ageRangeId = ageRangeId;
        this.roleId = roleId;
        this.amount = amount;
    }

    public Price(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getId() { return id; }
    public int getCompanyId() { return companyId; }
    public int getInsuranceTypeId() { return insuranceTypeId; }
    public int getSubscriptionId() { return subscriptionId; }
    public int getAgeRangeId() { return ageRangeId; }
    public int getRoleId() { return roleId; }
    public int getAmount() { return amount; }
}
