import exception.FileNotReadException;
import exception.MyFileWriterException;

public class Main {
    public static void main(String[] args) throws FileNotReadException, MyFileWriterException {
        CustomerPortal customerPortal = new CustomerPortal();
        customerPortal.showMenu();
    }
}
