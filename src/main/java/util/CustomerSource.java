package util;

import exception.FileNotReadException;
import exception.MyFileWriterException;

import java.util.List;

public class CustomerSource {

    private ReadDelimitedFile readDelimitedFile;
    private MyFileWriter myFileWriter;

    public CustomerSource() { }

    public CustomerSource(String fileName) {
        readDelimitedFile = new ReadDelimitedFileImpl(fileName);
        myFileWriter = new MyFileWriterImpl(fileName);
    }

    protected List<String[]> loadCustomerData() throws FileNotReadException {
        return readDelimitedFile.getData();
    }

    public void populateCustomers(List<Customer> customerList) throws FileNotReadException {
        List<String[]> customerData = loadCustomerData();

        customerData.forEach(rawCustomer -> {
            String customerEmail = rawCustomer[0];
            String customerForename = rawCustomer[1];
            String customerSurname = rawCustomer[2];
            String customerPassword = rawCustomer[3];

            customerList.add(new Customer(customerEmail, customerForename, customerSurname, customerPassword));
        });
    }

    public void insertCustomer(String customerEmailAddress, String customerForename, String customerSurname, String customerPassword) throws MyFileWriterException {
        String insertCustomerString = getInsertCustomerString(customerEmailAddress, customerForename, customerSurname, customerPassword);
        myFileWriter.write(insertCustomerString);
    }

    protected String getInsertCustomerString(String customerEmailAddress, String customerForename, String customerSurname, String customerPassword) {
        return customerEmailAddress + ", " + customerForename + ", " + customerSurname + ", " + customerPassword;
    }

    public void setReadDelimitedFile(ReadDelimitedFile readDelimitedFile) {
        this.readDelimitedFile = readDelimitedFile;
    }

    public void setMyFileWriter(MyFileWriter myFileWriter) {
        this.myFileWriter = myFileWriter;
    }
}
