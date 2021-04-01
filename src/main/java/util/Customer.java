package util;

import java.util.Objects;

public class Customer {

    private String emailAddress;
    private String forename;
    private String surname;
    private String password;

    public Customer(String emailAddress, String forename, String surname, String password) {
        this.emailAddress = emailAddress;
        this.forename = forename;
        this.surname = surname;
        this.password = password;
    }

    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
    public void setForename(String forename) {
        this.forename = forename;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailAddress() { return emailAddress; }
    public String getForename() { return forename; }
    public String getSurname() { return surname; }
    public String getPassword() { return password; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(emailAddress, customer.emailAddress) && Objects.equals(forename, customer.forename) && Objects.equals(surname, customer.surname) && Objects.equals(password, customer.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(emailAddress, forename, surname, password);
    }
}
