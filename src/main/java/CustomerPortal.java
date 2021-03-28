import exception.FileNotReadException;
import exception.MyFileWriterException;
import util.Customer;
import util.Display;

public class CustomerPortal {

    private Display display;
    private AllCustomers allCustomers;

    public CustomerPortal() throws FileNotReadException {
        display = new Display();
        allCustomers = new AllCustomers();
        allCustomers.populateCustomers();
    }


    public void signUp() throws MyFileWriterException {
        String customerEmailAddress = display.getInput("Enter New Email: ");
        String customerForename = display.getInput("Enter Your First Name: ");
        String customerSurname = display.getInput("Enter Your Last Name: ");
        String customerPassword = display.getInput("Enter New Password: ");

        Customer customer = allCustomers.getSingleCustomer(customerEmailAddress);

        if (customer == null) {
            allCustomers.insertCustomer(customerEmailAddress, customerForename, customerSurname, customerPassword);
            display.displayMessage("Great " + customerForename + ", You Are Now Successfully Signed Up!");
        } else {
            display.displayMessage("Customer With Email: " + customerEmailAddress + " Already Exists!");
        }
    }

    public void logIn() {
        String customerEmailAddress = display.getInput("Enter Your Email: ");
        String customerPassword = display.getInput("Enter Your Password: ");
        Customer customer = allCustomers.getSingleCustomer(customerEmailAddress);

        if (customer == null) {
            display.displayMessage("Please Sign Up!");
        }
        else if (customer.getPassword().equals(customerPassword)) {
            display.displayMessage("Logged In As: " + customerEmailAddress);
        }
        else {
            display.displayMessage("Uh Oh! Wrong Password. Try Again Later!");
        }
    }

    public void showMenu() throws MyFileWriterException {
        String menuChoice;

        do {
            String menuMessage = """
                    Welcome To The BBC Customer Portal!
                    1. Sign Up
                    2. Log In
                    3. Exit
                    """;
            menuChoice = display.getInput(menuMessage);

            switch (menuChoice) {
                case "1" -> signUp();
                case "2" -> logIn();
                case "3" -> display.displayMessage("Bye Bye! Hope To See You Again!");
                default -> display.displayMessage("Uh Oh! Input Not Recognised, Try Again!");
            }

        } while (!menuChoice.equals("3"));
    }

    public void setUserInput(Display display) {
        this.display = display;
    }

    public void setAllCustomers(AllCustomers allCustomers) {
        this.allCustomers = allCustomers;
    }
}