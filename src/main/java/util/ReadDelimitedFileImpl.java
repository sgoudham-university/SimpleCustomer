package util;

import exception.FileNotReadException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadDelimitedFileImpl implements ReadDelimitedFile {

    private String fileDelimiter = ",";
    private String filePathPrefix = "src/main/resources/";
    private String fullFilePath;

    public ReadDelimitedFileImpl() { }

    public ReadDelimitedFileImpl(String fileName) {
        fullFilePath = filePathPrefix + fileName;
    }

    @Override
    public List<String[]> getData() throws FileNotReadException {
        return getData(fullFilePath);
    }

    @Override
    public List<String[]> getData(String fileName) throws FileNotReadException {
        List<String[]> fileData = new ArrayList<>();

        File propertyFile = new File(fullFilePath);
        try (Scanner fileReader = new Scanner(propertyFile)) {
            while(fileReader.hasNextLine()) {
                String nextLine = fileReader.nextLine().replaceAll(" ", "");
                fileData.add(nextLine.split(fileDelimiter));
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new FileNotReadException(ioe.getMessage(), ioe);
        }

        return fileData;
    }

    @Override
    public void setFileDelimiter(String fileDelimiter){
        this.fileDelimiter = fileDelimiter;
    }

    @Override
    public void setFilePathPrefix(String filePathPrefix) {
        this.filePathPrefix = filePathPrefix;
    }

    @Override
    public void setFullFilePath(String fullFilePath) { this.fullFilePath = fullFilePath; }
}