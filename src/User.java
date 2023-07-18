package src;

import java.util.Scanner;
import javax.swing.JOptionPane;

public abstract class User
{
    protected String ID;
    protected String name;
    public abstract void menu();
    public abstract void displayDetails();
    public abstract boolean verificationID();

    public User() {
        ID = null;
        name = null;
    }

    public static void greet()
    {
        System.out.println("*****************************************************");
        System.out.println("*                  WELCOME TO                       *");
        System.out.println("*                      TMS                          *");
        System.out.println("*****************************************************");
    }

    public static int chooseOccupation()
    {
        String[] button = {"Staff", "Student"};
        int option = JOptionPane.showOptionDialog(null, "CHOOSE YOUR OCCUPATION\n(click (x) to exit the program)", "Verification",JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, button, null);
        int selection = 0;
        if(option == 1){
            selection = 2;
        } else if (option == 0){
            selection = 1;
        }
        return selection;
    }

    public String insertUserID(int selection)
    {
        Scanner scan = new Scanner(System.in);
        if (selection == 1) {
            System.out.print("Enter Staff ID: ");
        } else if (selection == 2) {
            System.out.print("Enter Student ID: ");
        }
        return scan.nextLine();
    }

    public String userID(int selection)
    {
        Scanner scan = new Scanner(System.in);
        if (selection == 1) {
            System.out.print("Enter Staff ID: ");
        } else if (selection == 2) {
            System.out.print("Enter Student ID: ");

        }
        ID = scan.nextLine();
        return ID;
    }

    public int logout() {
        int result = JOptionPane.showConfirmDialog(null,"Are you sure you would like to logout?","Logout",JOptionPane.YES_NO_OPTION);
        if (result == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(null,"You've been logged out!","Alert",JOptionPane.WARNING_MESSAGE);
        }
        return result;
    }
}

