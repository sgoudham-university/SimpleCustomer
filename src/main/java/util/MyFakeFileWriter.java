package util;

import java.util.ArrayList;
import java.util.List;

public class MyFakeFileWriter implements MyFileWriter {

    private String fileName;
    private String filePathPrefix = "src/main/resources/";
    private String fullFilePath;

    private final List<String> fileContents = new ArrayList<>();

    public MyFakeFileWriter() { }

    public MyFakeFileWriter(String fileName) {
        this.fileName = fileName;
        this.fullFilePath = filePathPrefix + fileName;
    }

    @Override
    public void write(String messageToWrite) {
        fileContents.add(messageToWrite);
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

    public List<String> getFileContents() {
        return fileContents;
    }
}
