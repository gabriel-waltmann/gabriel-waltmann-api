import java.util.Objects;

public class PersonAdmin extends Person {
    private final String authToken;

    public PersonAdmin(String firstName, String lastName, int age, double height, String gender, Date updatedAt, String authToken) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.height = height;
        this.isMale = Objects.equals(gender, "M");
        this.updatedAt = updatedAt;
        this.authToken = authToken;
    }

    @Override
    public String getFullName () {
        return this.firstName + " " + this.lastName + " (ADMIN)";
    }

    public int getAge () {
        return this.age;
    }

    public double getHeight () {
        return this.height;
    }

    public boolean isMale () {
        return this.isMale;
    }

    public String getAuthToken() {
        return this.authToken;
    }
}
