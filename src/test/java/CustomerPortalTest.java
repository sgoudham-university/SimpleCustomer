import exception.FileNotReadException;
import exception.MyFileWriterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import util.Customer;
import util.Display;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class CustomerPortalTest {

    @Mock
    Display displayMock;

    @Spy
    AllCustomers allCustomers;

    CustomerPortal customerPortal;

    @BeforeEach
    void setUp() throws FileNotReadException {
        MockitoAnnotations.openMocks(this);

        customerPortal = new CustomerPortal();
        customerPortal.setUserInput(displayMock);
        allCustomers.setCustomerList(createCustomerList());
        customerPortal.setAllCustomers(allCustomers);
    }

    @Test
    void successfullySignUp() throws MyFileWriterException {
        Customer expectedCustomer = createCustomer();
        String menuMessage = """
                    Welcome To The BBC Customer Portal!
                    1. Sign Up
                    2. Log In
                    3. Exit
                    """;
        String expectedCustomerEmail = expectedCustomer.getEmailAddress();
        String expectedCustomerForename = expectedCustomer.getForename();
        String expectedCustomerSurname = expectedCustomer.getSurname();
        String expectedCustomerPassword = expectedCustomer.getPassword();
        String expectedSignUpConfirmation = "Great " + expectedCustomerForename + ", You Are Now Successfully Signed Up!";
        String expectedGoodbyeMessage = "Bye Bye! Hope To See You Again!";

        when(displayMock.getInput(menuMessage))
                .thenReturn("1")
                .thenReturn("3");
        when(displayMock.getInput("Enter New Email: ")).thenReturn(expectedCustomerEmail);
        when(displayMock.getInput("Enter Your First Name: ")).thenReturn(expectedCustomerForename);
        when(displayMock.getInput("Enter Your Last Name: ")).thenReturn(expectedCustomerSurname);
        when(displayMock.getInput("Enter New Password: ")).thenReturn(expectedCustomerPassword);
        doNothing().when(allCustomers).insertCustomer(expectedCustomerEmail, expectedCustomerForename, expectedCustomerSurname, expectedCustomerPassword);

        customerPortal.showMenu();

        verify(displayMock, times(2)).getInput(menuMessage);
        verify(displayMock, times(1)).getInput("Enter New Email: ");
        verify(displayMock, times(1)).getInput("Enter Your First Name: ");
        verify(displayMock, times(1)).getInput("Enter Your Last Name: ");
        verify(displayMock, times(1)).getInput("Enter New Password: ");
        verify(displayMock, times(1)).displayMessage(expectedSignUpConfirmation);
        verify(displayMock, times(1)).displayMessage(expectedGoodbyeMessage);
        verifyNoMoreInteractions(displayMock);
    }

    @Test
    void failToSignUp() throws MyFileWriterException {
        Customer expectedCustomer = createSecondCustomer();
        String menuMessage = """
                    Welcome To The BBC Customer Portal!
                    1. Sign Up
                    2. Log In
                    3. Exit
                    """;
        String expectedCustomerEmail = expectedCustomer.getEmailAddress();
        String expectedCustomerForename = expectedCustomer.getForename();
        String expectedCustomerSurname = expectedCustomer.getSurname();
        String expectedCustomerPassword = expectedCustomer.getPassword();
        String expectedSignUpFailure = "Customer With Email: " + expectedCustomerEmail + " Already Exists!";
        String expectedGoodbyeMessage = "Bye Bye! Hope To See You Again!";

        when(displayMock.getInput(menuMessage))
                .thenReturn("1")
                .thenReturn("3");
        when(displayMock.getInput("Enter New Email: ")).thenReturn(expectedCustomerEmail);
        when(displayMock.getInput("Enter Your First Name: ")).thenReturn(expectedCustomerForename);
        when(displayMock.getInput("Enter Your Last Name: ")).thenReturn(expectedCustomerSurname);
        when(displayMock.getInput("Enter New Password: ")).thenReturn(expectedCustomerPassword);

        customerPortal.showMenu();

        verify(displayMock, times(2)).getInput(menuMessage);
        verify(displayMock, times(1)).getInput("Enter New Email: ");
        verify(displayMock, times(1)).getInput("Enter Your First Name: ");
        verify(displayMock, times(1)).getInput("Enter Your Last Name: ");
        verify(displayMock, times(1)).getInput("Enter New Password: ");
        verify(displayMock, times(1)).displayMessage(expectedSignUpFailure);
        verify(displayMock, times(1)).displayMessage(expectedGoodbyeMessage);
        verifyNoMoreInteractions(displayMock);
    }

    @Test
    void successfullyLogIn() throws MyFileWriterException {
        Customer expectedCustomer = createSecondCustomer();
        String menuMessage = """
                    Welcome To The BBC Customer Portal!
                    1. Sign Up
                    2. Log In
                    3. Exit
                    """;
        String expectedCustomerEmail = expectedCustomer.getEmailAddress();
        String expectedCustomerPassword = expectedCustomer.getPassword();
        String expectedLogInConfirmation = "Logged In As: " + expectedCustomerEmail;
        String expectedGoodbyeMessage = "Bye Bye! Hope To See You Again!";

        when(displayMock.getInput(menuMessage))
                .thenReturn("2")
                .thenReturn("3");
        when(displayMock.getInput("Enter Your Email: ")).thenReturn(expectedCustomerEmail);
        when(displayMock.getInput("Enter Your Password: ")).thenReturn(expectedCustomerPassword);

        customerPortal.showMenu();

        verify(displayMock, times(2)).getInput(menuMessage);
        verify(displayMock, times(1)).getInput("Enter Your Email: ");
        verify(displayMock, times(1)).getInput("Enter Your Password: ");
        verify(displayMock, times(1)).displayMessage(expectedLogInConfirmation);
        verify(displayMock, times(1)).displayMessage(expectedGoodbyeMessage);
        verifyNoMoreInteractions(displayMock);
    }

    @Test
    void failToLogin() throws MyFileWriterException {
        Customer expectedCustomer = createCustomer();
        String menuMessage = """
                    Welcome To The BBC Customer Portal!
                    1. Sign Up
                    2. Log In
                    3. Exit
                    """;
        String expectedCustomerEmail = expectedCustomer.getEmailAddress();
        String expectedCustomerPassword = expectedCustomer.getPassword();
        String expectedLogInFailure = "Please Sign Up!";
        String expectedGoodbyeMessage = "Bye Bye! Hope To See You Again!";

        when(displayMock.getInput(menuMessage))
                .thenReturn("2")
                .thenReturn("3");
        when(displayMock.getInput("Enter Your Email: ")).thenReturn(expectedCustomerEmail);
        when(displayMock.getInput("Enter Your Password: ")).thenReturn(expectedCustomerPassword);

        customerPortal.showMenu();

        verify(displayMock, times(2)).getInput(menuMessage);
        verify(displayMock, times(1)).getInput("Enter Your Email: ");
        verify(displayMock, times(1)).getInput("Enter Your Password: ");
        verify(displayMock, times(1)).displayMessage(expectedLogInFailure);
        verify(displayMock, times(1)).displayMessage(expectedGoodbyeMessage);
        verifyNoMoreInteractions(displayMock);
    }

    @Test
    void failToChooseMenuOption() throws MyFileWriterException {
        String menuMessage = """
                    Welcome To The BBC Customer Portal!
                    1. Sign Up
                    2. Log In
                    3. Exit
                    """;
        String expectedMenuOptionFailure = "Uh Oh! Input Not Recognised, Try Again!";
        String expectedGoodbyeMessage = "Bye Bye! Hope To See You Again!";

        when(displayMock.getInput(menuMessage))
                .thenReturn("invalidOption")
                .thenReturn("3");

        customerPortal.showMenu();

        verify(displayMock, times(2)).getInput(menuMessage);
        verify(displayMock, times(1)).displayMessage(expectedMenuOptionFailure);
        verify(displayMock, times(1)).displayMessage(expectedGoodbyeMessage);
        verifyNoMoreInteractions(displayMock);
    }

    @Test
    void failToLoginThenSuccessfullyLogin() throws MyFileWriterException {
        Customer expectedCustomer = createSecondCustomer();
        String menuMessage = """
                    Welcome To The BBC Customer Portal!
                    1. Sign Up
                    2. Log In
                    3. Exit
                    """;
        String expectedCustomerEmail = expectedCustomer.getEmailAddress();
        String expectedCustomerPassword = expectedCustomer.getPassword();
        String expectedLogInFailure = "Uh Oh! Wrong Password. Try Again Later!";
        String expectedLogInConfirmation = "Logged In As: " + expectedCustomerEmail;
        String expectedGoodbyeMessage = "Bye Bye! Hope To See You Again!";

        when(displayMock.getInput(menuMessage))
                .thenReturn("2")
                .thenReturn("2")
                .thenReturn("3");
        when(displayMock.getInput("Enter Your Email: "))
                .thenReturn(expectedCustomerEmail)
                .thenReturn(expectedCustomerEmail);
        when(displayMock.getInput("Enter Your Password: "))
                .thenReturn(expectedCustomerEmail)
                .thenReturn(expectedCustomerPassword);

        customerPortal.showMenu();

        verify(displayMock, times(3)).getInput(menuMessage);
        verify(displayMock, times(2)).getInput("Enter Your Email: ");
        verify(displayMock, times(2)).getInput("Enter Your Password: ");
        verify(displayMock, times(1)).displayMessage(expectedLogInFailure);
        verify(displayMock, times(1)).displayMessage(expectedLogInConfirmation);
        verify(displayMock, times(1)).displayMessage(expectedGoodbyeMessage);
        verifyNoMoreInteractions(displayMock);
    }

    private Customer createCustomer() {
        return new Customer("testEmail",
                "testForename",
                "testSurname",
                "testPassword");
    }

    private Customer createSecondCustomer() {
        return new Customer("testEmail2",
                "testForename2",
                "testSurname2",
                "testPassword2");
    }

    private List<Customer> createCustomerList() {
        return new ArrayList<>() {{
            add(new Customer("testEmail2", "testForename2", "testSurname2", "testPassword2"));
        }};
    }
}