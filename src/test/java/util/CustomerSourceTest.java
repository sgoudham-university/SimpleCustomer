package util;

import exception.FileNotReadException;
import exception.MyFileWriterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CustomerSourceTest {

    @Mock
    ReadDelimitedFile readDelimitedFileMock;

    @Mock
    MyFileWriter myFileWriterMock;

    ReadDelimitedFile readDelimitedFileStub;

    CustomerSource customerSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        String testCustomerPath = "src/test/resources/testCustomer.csv";

        ReadDelimitedFile testReadDelimitedFile = new ReadDelimitedFileImpl("customer.csv");
        testReadDelimitedFile.setFullFilePath(testCustomerPath);
        MyFileWriter myFileWriter = new MyFileWriterImpl("customer.csv");
        myFileWriter.setFullFilePath(testCustomerPath);

        customerSource = new CustomerSource();
        customerSource.setReadDelimitedFile(testReadDelimitedFile);
        customerSource.setMyFileWriter(myFileWriter);

        readDelimitedFileStub = new ReadDelimitedFileStub();
    }

    @Test
    void successfullyRetrieveCustomerData() throws FileNotReadException {
        String expectedFirstCustomerEmail = "testEmail";

        List<String[]> actualCustomerData = customerSource.loadCustomerData();

        String actualFirstCustomerEmail = actualCustomerData.get(1)[0];
        assertThat(actualFirstCustomerEmail, is(expectedFirstCustomerEmail));
    }

    @Test
    void successfullyRetrieveCustomerDataStub() throws FileNotReadException {
        String expectedFirstCustomerEmail = "testEmail";

        customerSource.setReadDelimitedFile(readDelimitedFileStub);
        List<String[]> actualCustomerData = customerSource.loadCustomerData();

        String actualFirstCustomerEmail = actualCustomerData.get(1)[0];
        assertThat(actualFirstCustomerEmail, is(expectedFirstCustomerEmail));
    }

    @Test
    void successfullyPopulateCustomerList() throws FileNotReadException {
        List<Customer> actualCustomerList = new ArrayList<>();
        Customer expectedCustomer = createCustomer();
        List<String[]> expectedCustomerData = readDelimitedFileStub.getData();

        customerSource.setReadDelimitedFile(readDelimitedFileMock);
        when(readDelimitedFileMock.getData()).thenReturn(expectedCustomerData);

        assertDoesNotThrow(() -> customerSource.populateCustomers(actualCustomerList));

        Customer actualCustomer = actualCustomerList.get(1);
        assertThat(actualCustomer, is(expectedCustomer));
    }

    @Test
    void failToMapToCustomerList() throws FileNotReadException {
        List<Customer> actualCustomerList = new ArrayList<>();
        String expectedExceptionMessage = "Failure To Read In File!";
        Exception expectedException = new FileNotReadException(expectedExceptionMessage, new IOException());

        when(readDelimitedFileMock.getData()).thenThrow(expectedException);
        customerSource.setReadDelimitedFile(readDelimitedFileMock);

        Exception thrownException = assertThrows(FileNotReadException.class, () -> customerSource.populateCustomers(actualCustomerList));

        assertThat(thrownException.getMessage(), is(expectedExceptionMessage));
    }

    @Test
    void successfullyInsertCustomer() {
        Customer expectedCustomer = createCustomer();
        String expectedCustomerEmail = expectedCustomer.getEmailAddress();
        String expectedCustomerForename = expectedCustomer.getForename();
        String expectedCustomerSurname = expectedCustomer.getSurname();
        String expectedCustomerPassword = expectedCustomer.getPassword();

        assertDoesNotThrow(() -> customerSource.insertCustomer(expectedCustomerEmail, expectedCustomerForename, expectedCustomerSurname, expectedCustomerPassword));
    }

    @Test
    void successfullyInsertCustomerFake() throws MyFileWriterException {
        Customer expectedCustomer = createCustomer();
        String expectedCustomerEmail = expectedCustomer.getEmailAddress();
        String expectedCustomerForename = expectedCustomer.getForename();
        String expectedCustomerSurname = expectedCustomer.getSurname();
        String expectedCustomerPassword = expectedCustomer.getPassword();
        String expectedInsertCustomerString = getInsertCustomerString(expectedCustomerEmail, expectedCustomerForename, expectedCustomerSurname, expectedCustomerPassword);

        MyFakeFileWriter myFakeFileWriter = new MyFakeFileWriter();
        myFakeFileWriter.setFullFilePath("src/test/resources/testCustomer.csv");
        customerSource.setMyFileWriter(myFakeFileWriter);

        customerSource.insertCustomer(expectedCustomerEmail, expectedCustomerForename, expectedCustomerSurname, expectedCustomerPassword);

        List<String> actualFileContents = myFakeFileWriter.getFileContents();
        String actualFileFirstCustomer = actualFileContents.get(0);
        assertThat(actualFileFirstCustomer, is(expectedInsertCustomerString));
    }

    @Test
    void failToInsertCustomer() throws MyFileWriterException {
        Customer expectedCustomer = createCustomer();
        String expectedCustomerEmail = expectedCustomer.getEmailAddress();
        String expectedCustomerForename = expectedCustomer.getForename();
        String expectedCustomerSurname = expectedCustomer.getSurname();
        String expectedCustomerPassword = expectedCustomer.getPassword();
        String expectedInsertCustomerString = getInsertCustomerString(expectedCustomerEmail, expectedCustomerForename, expectedCustomerSurname, expectedCustomerPassword);

        String expectedExceptionMessage = "Failure To Write To File!";
        Exception expectedException = new MyFileWriterException(expectedExceptionMessage, new IOException());

        doThrow(expectedException).when(myFileWriterMock).write(expectedInsertCustomerString);
        customerSource.setMyFileWriter(myFileWriterMock);

        Exception thrownException = assertThrows(MyFileWriterException.class, () -> {
            customerSource.insertCustomer(expectedCustomerEmail, expectedCustomerForename, expectedCustomerSurname, expectedCustomerPassword);
        });

        assertThat(thrownException.getMessage(), is(expectedExceptionMessage));
    }

    private Customer createCustomer() {
        return new Customer("testEmail",
                "testForename",
                "testSurname",
                "testPassword");
    }

    private String getInsertCustomerString(String customerEmailAddress, String customerForename, String customerSurname, String customerPassword) {
        return customerEmailAddress + ", " + customerForename + ", " + customerSurname + ", " + customerPassword;
    }
}