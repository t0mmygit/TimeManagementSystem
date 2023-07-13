package src;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

public class Timetable
{
    private String course;
    private String day;
    private String startTime;
    private String endTime;

    public Timetable() {
        course = null;
        day = null;
        startTime = null;
        endTime = null;
    }

    public void displayTimetable(String userID, int selection, int menuSelection) {
        int DAYS_PER_WEEK = 7;
        int HOURS_PER_DAY = 11;
        BufferedReader reader;
        String FILE_COURSE = "Course.txt";
        String FILE_DATABASE = "database.txt";
        String FILE_USERDATA = "userDatabase.txt";
        String[] days = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
        String[] time = {"8:00","9:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00","18:00"};
        String[][] timetable = new String[DAYS_PER_WEEK][HOURS_PER_DAY];
        List<String> registeredCourses = new ArrayList<>();
        String studentID = null;
        String studentName = null;
        String staffID = null;
        boolean validStudent = true;
        boolean displayTable = true;
        String line = null;
        if (selection == 1 && menuSelection == 3) { // Staff view Student's timetable
            try {
                Scanner scan = new Scanner(System.in);
                Set<String> IDs = new HashSet<>();
                reader = new BufferedReader(new FileReader(FILE_USERDATA));
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String ID = parts[0];
                    studentName = parts[1];
                    String type = parts[2];
                    if (type.equals("2")) {
                        IDs.add(ID);
                    }
                }
                reader.close();
                boolean validID = false;
                while (!validID) {
                    System.out.print("[3] Enter Student ID: ");
                    studentID = scan.next();
                    if (IDs.contains(studentID)) {
                        validID = true;
                        System.out.println("Student Name: " + studentName);
                    } else {
                        int choice = JOptionPane.showConfirmDialog(null, "Wrong input, would you like to continue?", "Confirmation", JOptionPane.YES_NO_OPTION);
                        if (choice == JOptionPane.NO_OPTION || choice == JOptionPane.CLOSED_OPTION) {
                            displayTable = false;
                            break;
                        }
                    }
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else if (selection == 1 && menuSelection == 2) {
            staffID = userID;
            validStudent = false;
        } else if (selection == 2) {
            studentID = userID;
        }
        if (validStudent || menuSelection == 3) {
            try {
                reader = new BufferedReader(new FileReader(FILE_DATABASE));
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String ID = parts[0];
                    String course = parts[2];
                    if (ID.equals(studentID)) {
                        registeredCourses.add(course);
                    }
                }
                reader.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } else if (!validStudent && menuSelection == 2) {
            try {
                reader = new BufferedReader(new FileReader(FILE_DATABASE));
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String ID = parts[0];
                    String course = parts[2];
                    if (ID.equals(staffID)) {
                        registeredCourses.add(course);
                    }
                }
                reader.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
        // Construct Timetable
        if (displayTable) {
            try {
                reader = new BufferedReader(new FileReader(FILE_COURSE));
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String course = parts[0];
                    if (!registeredCourses.contains(course)) {
                        continue;
                    }
                    String day = parts[1];
                    String startTime = parts[2];
                    String endTime = parts[3];
                    String[] startTimeParts = startTime.split(":");
                    String[] endTimeParts = endTime.split(":");
                    int startHour = Integer.parseInt(startTimeParts[0]);
                    int endHour = Integer.parseInt(endTimeParts[0]);
                    int startHourIndex = startHour - 8;
                    int endHourIndex = endHour - 8;
                    int dayIndex = -1;
                    day = day.toUpperCase();
                    switch (day) {
                        case "MONDAY" -> dayIndex = 0;
                        case "TUESDAY" -> dayIndex = 1;
                        case "WEDNESDAY" -> dayIndex = 2;
                        case "THURSDAY" -> dayIndex = 3;
                        case "FRIDAY" -> dayIndex = 4;
                        case "SATURDAY" -> dayIndex = 5;
                        case "SUNDAY" -> dayIndex = 6;
                        default -> System.out.println("Invalid Day!");
                    }
                    for (int i = startHourIndex; i <= endHourIndex; i++) {
                        timetable[dayIndex][i] = course;
                    }
                }
                reader.close();
                if (selection == 2) {
                    System.out.println("[1][TIMETABLE]");
                } else if (menuSelection == 2) {
                    System.out.println("[2][TIMETABLE]");
                }
                System.out.println("======================================================================================================================================");
                System.out.printf("|%11s|","");
                for (String times : time) {
                    System.out.printf(" %7s%3s", times, "|");
                }
                System.out.println("\n======================================================================================================================================");
                for (int i = 0; i < DAYS_PER_WEEK; i++) {
                    System.out.printf("| %9s |",days[i]);
                    for (int j = 0; j < HOURS_PER_DAY; j++) {
                        String courseSlot = timetable[i][j];
                        if (courseSlot == null) {
                            courseSlot = "    ";
                        }
                        System.out.printf(" %7s%3s", courseSlot, "|");
                    }
                    if (i < DAYS_PER_WEEK-1) {
                        System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------");
                    }
                }
                System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------\n");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
