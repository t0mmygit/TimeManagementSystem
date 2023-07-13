package src;

import javax.swing.*;

public class TimeManagementMain
{
    public static void MessageOptionPane(int number)
    {
        if (number == 0) {
            JOptionPane.showMessageDialog(null,"Invalid input, Please try again!","Alert", JOptionPane.WARNING_MESSAGE);
        } else if (number == 1) {
            JOptionPane.showMessageDialog(null,"Updated Successfully!");
        }
    }

    public static void clearTerminal() {
        System.out.print('\u000c');
    }

    public static void main(String[] args)
    {
        Staff staff = new Staff();
        Student std = new Student();
        Course course = new Course();
        Timetable tt = new Timetable();

        // PHASE 1 - USER'S VERIFICATION (ID)
        boolean newUser = true;
        while (newUser) {
            int selection = 0;
            boolean verificationID = false;
            boolean repeatSelection = false;

            while (!repeatSelection) {
                String ID = null;
                selection = staff.askVerify();
                if (selection == 1 || selection == 2) {
                    staff.greet();
                    repeatSelection = true;
                    while (!verificationID)
                    {
                        if (selection == 1) { // Staff
                            ID = staff.userID(selection);
                            staff = new Staff(ID);
                            verificationID = staff.verificationID();
                            if (verificationID) {
                                JOptionPane.showMessageDialog(null,"Login Successfully!\nWelcome, " + staff.getName() + "!");
                                clearTerminal();
                            } else {
                                MessageOptionPane(0);
                            }
                        } else if (selection == 2) { // Student
                            ID = std.userID(selection);
                            std = new Student(ID);
                            verificationID = std.verificationID();
                            if (verificationID) {
                                JOptionPane.showMessageDialog(null,"Login Successfully!\nWelcome, " + std.getName() + "!");
                                clearTerminal();
                            } else {
                                MessageOptionPane(0);
                            }
                        } else {
                            MessageOptionPane(0);
                        }
                    }
                } else {
                    int result = JOptionPane.showConfirmDialog(null,"Are you sure to exit the program?","Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
                    if (result == JOptionPane.YES_OPTION) {
                        JOptionPane.showMessageDialog(null,"Program Terminated!","Alert",JOptionPane.WARNING_MESSAGE);
                        System.exit(0);
                    }
                }
            }
            // PHASE 2: MENU SELECTION FOR STAFF & STUDENT
            // STAFF MENU(1)
            if (selection == 1) {
                String option = null;
                boolean displayMenu = true;
                do {
                    if (displayMenu) {
                        staff.display();
                        staff.menu();
                    }
                    displayMenu = true;
                    while (true) {
                        option = JOptionPane.showInputDialog(null,"Please choose between [1][2][3][4]","Selection", JOptionPane.PLAIN_MESSAGE);
                        if (option == null) {
                            JOptionPane.showMessageDialog(null,"Please enter (4) to exit the program!","Alert", JOptionPane.WARNING_MESSAGE);
                        } else if (option.equals("")) {
                            MessageOptionPane(0);
                        } else {
                            break;
                        }
                    }
                    try {
                        int optionNum = Integer.parseInt(option);
                        if (optionNum == 1) {
                            while (true) {
                                clearTerminal();
                                staff.display();
                                staff.menu();
                                course.displayCourse(selection, staff);
                                course.addNewCourse();
                                int choice = JOptionPane.showConfirmDialog(null, "Would you like to continue adding new course?", "Add new course", JOptionPane.YES_NO_OPTION);
                                if (choice == JOptionPane.NO_OPTION || choice == JOptionPane.CLOSED_OPTION) {
                                    clearTerminal();
                                    break;
                                }
                            }
                        } else if (optionNum == 2) {
                            tt.displayTimetable(staff.getID(), 1, optionNum);
                            displayMenu = false;
                        } else if (optionNum == 3) {
                            tt.displayTimetable(std.getID(), 1, optionNum);
                            displayMenu = false;
                        } else if (optionNum == 4) {
                            int result = JOptionPane.showConfirmDialog(null,"Are you sure you would like to Logout?","Logout",JOptionPane.YES_NO_OPTION);
                            if (result == JOptionPane.YES_OPTION) {
                                JOptionPane.showMessageDialog(null,"You've been logged out!","Alert",JOptionPane.WARNING_MESSAGE);
                                System.out.print('\u000c');
                                break;
                            } else {
                                displayMenu = false;
                            }
                        } else {
                            MessageOptionPane(0);
                            displayMenu = false;
                        }
                    } catch (NumberFormatException NFE) {
                        MessageOptionPane(0);
                        displayMenu = false;
                    }
                } while (true);
            }
            // STUDENT MENU(2)
            if (selection == 2) {
                String option = null;
                boolean repeatStudentMenu = true;
                boolean displayMenu = true;
                do {
                    if (displayMenu) {
                        std.display();
                        std.menu();
                    }
                    displayMenu = true;
                    while (true) {
                        option = JOptionPane.showInputDialog(null,"Please choose between [1][2][3][4]","Selection",JOptionPane.PLAIN_MESSAGE);
                        if (option == null) {
                            JOptionPane.showMessageDialog(null,"Please enter (4) to exit the program!","Alert",JOptionPane.WARNING_MESSAGE);
                        } else if (option.equals("")){
                            MessageOptionPane(0);
                        } else {
                            break;
                        }
                    }
                    try {
                        int optionNum = Integer.parseInt(option);
                        if (optionNum == 1) {
                            tt.displayTimetable(std.getID(), 2, optionNum);
                            displayMenu = false;
                        } else if (optionNum == 2) {
                            while (true) {
                                clearTerminal();
                                std.display();
                                std.menu();
                                course.enrollCourse(std);
                                int choice = JOptionPane.showConfirmDialog(null, " Would you like to register another course?", "Confirmation", JOptionPane.YES_NO_OPTION);
                                clearTerminal();
                                if (choice == JOptionPane.NO_OPTION || choice == JOptionPane.CLOSED_OPTION) {
                                    break;
                                } else {
                                    std.display();
                                    std.menu();
                                }
                            }
                        } else if (optionNum == 3) {
                            while (true) {
                                clearTerminal();
                                std.display();
                                std.menu();
                                boolean valid = course.dropCourse(std);
                                if (valid) {
                                    int choice = JOptionPane.showConfirmDialog(null, "Would you like to drop another course?", "Confirmation", JOptionPane.YES_NO_OPTION);
                                    System.out.print('\u000c');
                                    if (choice == JOptionPane.NO_OPTION || choice == JOptionPane.CLOSED_OPTION) {
                                        break;
                                    } else {
                                        std.display();
                                        std.menu();
                                    }
                                } else {
                                    displayMenu = false;
                                    break;
                                }
                            }
                        } else if (optionNum == 4) {
                            int result = std.logout();
                            if (result == 0) {
                                repeatStudentMenu = false;
                            } else {
                                displayMenu = false;
                            }
                        } else {
                            MessageOptionPane(0);
                            displayMenu = false;
                        }
                    } catch (NumberFormatException NFE) {
                        MessageOptionPane(0);
                        displayMenu = false;
                    }
                } while (repeatStudentMenu);
            }
        }
    }
}
