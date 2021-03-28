package util;

import java.util.ArrayList;
import java.util.List;

public class ReadDelimitedFileStub implements ReadDelimitedFile {

    @Override
    public List<String[]> getData() {
        return getData("stub");
    }

    @Override
    public List<String[]> getData(String fileName) {
        List<String[]> fileData = new ArrayList<>();

        String[] columns = new String[] {
                "emailAddress", "forename", "surname", "password"
        };
        String[] row1 = new String[] {
                "testEmail", "testForename" , "testSurname", "testPassword"
        };
        String[] row2 = new String[] {
                "matthew.barr@glasgow.ac.uk", "Matt" , "Barr", "4321"
        };

        fileData.add(columns);
        fileData.add(row1);
        fileData.add(row2);

        return fileData;
    }

    @Override
    public void setFileDelimiter(String fileDelimiter) {

    }

    @Override
    public void setFilePathPrefix(String filePathPrefix) {

    }

    @Override
    public void setFullFilePath(String fullFilePath) {

    }
}
