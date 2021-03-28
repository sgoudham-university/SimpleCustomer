package util;

import exception.FileNotReadException;

import java.util.List;

public interface ReadDelimitedFile {

    List<String[]> getData() throws FileNotReadException;
    List<String[]> getData(String fileName) throws FileNotReadException;
    void setFileDelimiter(String fileDelimiter);
    void setFilePathPrefix(String filePathPrefix);
    void setFullFilePath(String fullFilePath);
}
