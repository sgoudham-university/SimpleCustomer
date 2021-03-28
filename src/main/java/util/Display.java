package util;

import java.util.Scanner;

public class Display {

    private final Scanner userInput = new Scanner(System.in);

    public String getInput(String message) {
        displayMessage(message);
        return userInput.nextLine();
    }

    public void displayMessage(String message) {
        System.out.println(message + "\n");
    }
}
