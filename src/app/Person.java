package app;

/**
 * Represents every single person in the search window
 * We need a person's age and their role to search for their insurance
 * in addition to the company and subscription
 */

public class Person {
    private final int age;
    private final Role role;

    public Person(int age, Role role) {
        this.age = age;
        this.role = role;
    }

    public int getAge() { return this.age; }
    public Role getRole() { return this.role; }

    @Override
    public String toString() {
        return "Person{age=" + age + ", role=" + role.getName() + "}";
    }
}
