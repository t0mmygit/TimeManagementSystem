package src;

import javax.swing.*;

public class TimeManagementMain {
    public static void MessageOptionPane(String status)
    {
        if (status.equalsIgnoreCase("failed")) {
            JOptionPane.showMessageDialog(null,"Invalid input, Please try again!","Alert", JOptionPane.WARNING_MESSAGE);
        } else if (status.equalsIgnoreCase("success")) {
            JOptionPane.showMessageDialog(null,"Updated Successfully!");
        }
    }

    public static void clearTerminal() {
        System.out.print('\u000c');
    } // This will only work on certain Terminal/Console

    public static void main(String[] args) {
        Timetable timetable = new Timetable();;
        Course course = new Course();

        do {
            boolean verificationStatus = false;
            int occupationType = User.chooseOccupation();
            if (occupationType == 1) { // Staff Interaction
                Staff staff = new Staff();
                User.greet();

                while (!verificationStatus) {
                    String staffID = staff.insertUserID(occupationType);
                    staff.setStaffID(staffID);
                    verificationStatus = staff.verificationID();

                    if (verificationStatus) {
                        JOptionPane.showMessageDialog(null,
                                "Login Successfully! \nWelcome, " + staff.getName() + "!");
                    } else {
                        String[] button = { "Yes", "No"};
                        int option = JOptionPane.showOptionDialog(null, "Invalid Input! Would you like to continue?", "Input ID",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, button, null);
                        if (option != 0){
                            break;
                        }
                    }
                }
                if (verificationStatus) {
                    staff.displayDetails();
                }

                while (verificationStatus) {
                    staff.menu();
                    String option = null;
                    while (true) {
                        option = JOptionPane.showInputDialog(null,
                                "Please choose between [1][2][3][4]","Selection", JOptionPane.PLAIN_MESSAGE);
                        if (option == null) {
                            JOptionPane.showMessageDialog(null,
                                    "Please enter (4) to exit the program!","Alert",JOptionPane.WARNING_MESSAGE);
                        } else if (option.equals("")) {
                            MessageOptionPane("failed");
                        } else {
                            break;
                        }
                    }
                    int optionNum = Integer.parseInt(option);
                    if (optionNum == 1) {
                        while (true) {
                            course.displayCourse(occupationType, staff);
                            course.addNewCourse();
                            int choice = JOptionPane.showConfirmDialog(null,
                                    "Would you like to continue adding new course?", "Add new course", JOptionPane.YES_NO_OPTION);
                            if (choice == JOptionPane.NO_OPTION || choice == JOptionPane.CLOSED_OPTION) {
                                break;
                            }
                        }
                    } else if (optionNum == 2) {
                        timetable.displayTimetable(staff.getID(), occupationType, optionNum);
                    } else if (optionNum == 3) {
                        timetable.displayTimetable(null, occupationType, optionNum);
                    } else if (optionNum == 4) {
                        int result = staff.logout();
                        if (result == 0) {
                            break;
                        }
                    }
                }
            } else if (occupationType == 2) { // Student Interaction
                Student student = new Student();

                User.greet();
                while (!verificationStatus) {
                    String studentID = student.insertUserID(occupationType);
                    student.setStudentID(studentID);
                    verificationStatus = student.verificationID();

                    if (verificationStatus) {
                        JOptionPane.showMessageDialog(null,
                                "Login Successfully! \nWelcome, " + student.getName() + "!");
                        clearTerminal();
                    } else {
                        String[] button = { "Yes", "No"};
                        int option = JOptionPane.showOptionDialog(null, "Invalid Input! Would you like to continue?", "Input ID",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, button, null);
                        if (option != 0){
                            break;
                        }
                    }
                }
                if (verificationStatus) {
                    StudentType studentType = student.getStudentType();
                    if (studentType.equals(StudentType.NORMAL)) {
                        Student nStudent = new NormalStudent(student);
                        nStudent.displayDetails();
                    } else if (studentType.equals(StudentType.REPEATER)) {
                        Student rStudent = new RepeaterStudent(student);
                        rStudent.displayDetails();
                    } else {
                        Student dsStudent = new DistanceStudyStudent(student);
                        dsStudent.displayDetails();
                    }
                }

                while (verificationStatus) {
                    student.menu();
                    String option = null;
                    while (true) {
                        option = JOptionPane.showInputDialog(null,
                                "Please choose between [1][2][3][4]","Selection", JOptionPane.PLAIN_MESSAGE);
                        if (option == null) {
                            JOptionPane.showMessageDialog(null,"Please enter (4) to exit the program!","Alert",JOptionPane.WARNING_MESSAGE);
                        } else if (option.equals("")) {
                            MessageOptionPane("failed");
                        } else {
                            break;
                        }
                    }
                    int optionNum = Integer.parseInt(option);
                    if (optionNum == 1) {
                        timetable.displayTimetable(student.getID(), occupationType, optionNum);
                    } else if (optionNum == 2) {
                        while(true) {
                            course.enrollCourse(student);
                            int choice = JOptionPane.showConfirmDialog(null,
                                    "Would you like to register another course?", "Confirmation", JOptionPane.YES_NO_OPTION);
                            if (choice == JOptionPane.NO_OPTION || choice == JOptionPane.CLOSED_OPTION) {
                                break;
                            }
                        }
                    } else if (optionNum == 3) {
                        while (true) {
                            boolean dropped = course.dropCourse(student);
                            if (dropped) {
                                int choice = JOptionPane.showConfirmDialog(null,
                                        "Would you like to drop another course?", "Confirmation", JOptionPane.YES_NO_OPTION);
                                if (choice == JOptionPane.NO_OPTION || choice == JOptionPane.CLOSED_OPTION) {
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                    } else if (optionNum == 4) {
                        int result = student.logout();
                        if (result == 0) {
                            break;
                        }
                    } else {
                        MessageOptionPane("failed");
                    }
                }
            } else {
                int result = JOptionPane.showConfirmDialog(null,
                        "Are you sure to exit the program?","Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);

                if (result == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null,"Program Terminated!","Alert",JOptionPane.WARNING_MESSAGE);
                    System.exit(0);
                }
            }
        } while(true);
    }
}