package util;

import exception.MyFileWriterException;

public interface MyFileWriter {
    void write(String messageToWrite) throws MyFileWriterException;
    void refreshPath();
    void setFileName(String fileName);
    void setFilePathPrefix(String filePathPrefix);
    void setFullFilePath(String fullFilePath);
}
