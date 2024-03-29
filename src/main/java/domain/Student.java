package domain;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Student {
    /*
    TODO 4.1 The length of the name and secondName must be between 4 an 50 characters
    The name and secondName must begin with capital letters: use the regrexp "^[A-Z].+"

    A message must be given for all errors in all todos in this file
    When finished here look for TODO4.4
     */
    @NotNull(message = "username cannot be null")
    private String name;

    @NotNull(message = "Second name cannot be null")
    private String secondName;

    /*
    TODO 4.2 The email must follow the regrexp "\\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\\b"
     */
    @NotNull(message = "email cannot be null")
   private String email;

    /*
    TODO 4.3 The password length must be between 4 and 50
     */
    @NotNull(message = "password cannot be null")
    private String password;

    private List<Registration> registrations;

    public Student() {
        registrations = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Registration> getRegistrations() {
        return registrations;
    }

    public void setRegistration(Registration registration) {
        registrations.add(registration);
    }

    public boolean passedSubject(Subject subject) {
        return registrations.stream().anyMatch(r -> r.passedSubject(subject));
    }

    public List<Subject> getPassedSubjects() {
        return registrations.stream().flatMap(r -> r.getPassedSubjects().stream()).collect(Collectors.toList());
    }

    public List<Subject> getNonEvaluatedSubjects() {
        return registrations.stream().flatMap(r -> r.getNonEvaluatedSubjects().stream()).collect(Collectors.toList());
    }
    public Set<Subject> getAllRegisteredSubjects() {
        return registrations.stream().flatMap(r -> r.getSubjects().stream()).collect(Collectors.toSet());

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;

        Student student = (Student) o;

        return email.equals(student.email);
    }

    @Override
    public int hashCode() {
        return email.hashCode();
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", secondName='" + secondName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", registrations='" + registrations.size() + '\'' +
                '}';
    }
}
