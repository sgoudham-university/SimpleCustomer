import exception.FileNotReadException;
import exception.MyFileWriterException;
import util.Customer;
import util.CustomerSource;

import java.util.ArrayList;
import java.util.List;

public class AllCustomers {

    private List<Customer> customerList = new ArrayList<>();

    private CustomerSource customerSource;

    public AllCustomers() {
        customerSource = new CustomerSource("customer.csv");
    }

    public void populateCustomers() throws FileNotReadException {
        customerSource.populateCustomers(customerList);
    }

    public void insertCustomer(String customerEmailAddress, String customerForename, String customerSurname, String customerPassword) throws MyFileWriterException {
        customerSource.insertCustomer(customerEmailAddress, customerForename, customerSurname, customerPassword);
    }

    public Customer getSingleCustomer(String customerEmailAddress) {
        Customer customer = null;

        for (Customer currentCustomer : customerList) {
            if (currentCustomer.getEmailAddress().equals(customerEmailAddress)) {
                customer = currentCustomer;
                break;
            }
        }

        return customer;
    }

    public void setCustomerList(List<Customer> customerList) {
        this.customerList = customerList;
    }

    public void setCustomerSource(CustomerSource customerSource) {
        this.customerSource = customerSource;
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }
}
