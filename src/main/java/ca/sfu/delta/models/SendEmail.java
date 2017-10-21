package ca.sfu.delta.models;



public class SendEmail {
    private String firstName;
    private String lastName;
    private String emailAddress;

    public SendEmail(){

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName != null && !firstName.isEmpty()) {
            this.firstName = firstName;
        } else {
            throw new IllegalArgumentException("Could not find First Name.");
        }
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName != null && !lastName.isEmpty()) {
            this.lastName = lastName;
        } else {
            throw new IllegalArgumentException("Could not find Last Name.");
        }
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        if (emailAddress != null && !emailAddress.isEmpty()) {
            this.emailAddress = emailAddress;
        } else {
            throw new IllegalArgumentException("Could not find First Name.");
        }
    }

    @Override
    public String toString() {
        return "SendEmail{" +
                "firstName='" + firstName + "\'" +
                ", lastName='" + lastName + "\'" +
                ", emailAddress=" + emailAddress + "}";
    }
}
