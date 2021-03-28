import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import util.Customer;
import util.CustomerSource;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import static org.junit.jupiter.api.Assertions.*;

class AllCustomersTest {

    @Mock
    CustomerSource customerSourceMock;

    AllCustomers allCustomers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        allCustomers = new AllCustomers();
        allCustomers.setCustomerSource(customerSourceMock);
    }

    @Test
    void successfullyRetrieveSingleCustomer() {
        Customer expectedCustomer = createCustomer();
        List<Customer> actualCustomerList = createCustomerList();

        allCustomers.setCustomerList(actualCustomerList);
        Customer actualCustomer = allCustomers.getSingleCustomer(expectedCustomer.getEmailAddress());

        assertThat(actualCustomer, is(expectedCustomer));
    }

    @Test
    void failToRetrieveSingleCustomer() {
        Customer expectedCustomer = createInvalidCustomer();
        List<Customer> actualCustomerList = createCustomerList();

        allCustomers.setCustomerList(actualCustomerList);
        Customer actualCustomer = allCustomers.getSingleCustomer(expectedCustomer.getEmailAddress());

        assertNull(actualCustomer);
    }

    private Customer createCustomer() {
        return new Customer("testEmail",
                "testForename",
                "testSurname",
                "testPassword");
    }

    private Customer createInvalidCustomer() {
        return new Customer("invalidTestEmail",
                "invalidTestForename",
                "invalidTestSurname",
                "invalidTestPassword");
    }

    private List<Customer> createCustomerList() {
        return new ArrayList<>() {{
            add(new Customer("testEmail", "testForename", "testSurname", "testPassword"));
            add(new Customer("testEmail2", "testForename2", "testSurname2", "testPassword2"));
        }};
    }
}