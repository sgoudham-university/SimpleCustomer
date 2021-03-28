package util;

import exception.MyFileWriterException;

import java.io.FileWriter;
import java.io.IOException;

public class MyFileWriterImpl implements MyFileWriter {

    private String fileName;
    private String filePathPrefix = "src/main/resources/";
    private String fullFilePath;

    public MyFileWriterImpl() { }

    public MyFileWriterImpl(String fileName) {
        this.fileName = fileName;
        this.fullFilePath = filePathPrefix + fileName;
    }

    @Override
    public void write(String messageToWrite) throws MyFileWriterException {
        try (FileWriter fileWriter = new FileWriter(fullFilePath, true)) {
            fileWriter.write("\n" + messageToWrite);
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new MyFileWriterException(ioe.getMessage(), ioe);
        }
    }

    @Override
    public void refreshPath() {
        this.fullFilePath = filePathPrefix + fileName;
    }

    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void setFilePathPrefix(String filePathPrefix) {
        this.filePathPrefix = filePathPrefix;
    }

    @Override
    public void setFullFilePath(String fullFilePath) {
        this.fullFilePath = fullFilePath;
    }
}
