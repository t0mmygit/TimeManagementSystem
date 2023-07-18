package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.swing.JOptionPane;

public class Timetable {
    private String[] days;
    private String[] time;

    Timetable() {
        days = null;
        time = null;
    }

    public String[] getDays() {
        return days;
    }

    public void setDays(String[] days) {
        this.days = days;
    }

    public String[] getTime() {
        return time;
    }

    public void setTime(String[] time) {
        this.time = time;
    }

    public void displayTimetable(String userID, int occupationType, int menuSelection) {
        int DAYS_PER_WEEK = 7;
        int HOURS_PER_DAY = 11;
        BufferedReader reader;
        setDays(new String[]{"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"});
        setTime(new String[]{"8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00"});
        String[][] timetable = new String[DAYS_PER_WEEK][HOURS_PER_DAY];
        List<String> registeredCourses = new ArrayList<>();
        String studentID = null;
        String staffID = null;
        boolean validStudent = true;
        boolean displayTable = true;
        String line;
        if (occupationType == 1 && menuSelection == 3) { // Staff view Student's timetable
            try {
                Scanner scan = new Scanner(System.in);
                List<Student> students = new ArrayList<>();
                reader = PropertiesReader.PropertiesCall("studentdata");
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String ID = parts[0];
                    String studentName = parts[1];

                    Student student = new Student(ID, studentName);
                    students.add(student);
                }
                reader.close();

                boolean validID = false;
                while (!validID) {
                    System.out.print("[3] Enter Student ID: ");
                    studentID = scan.nextLine();

                    for (Student student : students) {
                        if (student.getID().equalsIgnoreCase(studentID)) {
                            System.out.println("Student Name: " + student.getName());
                            validID = true;
                            break;
                        }
                    }
                    if (!validID) {
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
        } else if (occupationType == 1 && menuSelection == 2) {
            staffID = userID;
            validStudent = false;
        } else if (occupationType == 2) {
            studentID = userID;
        }
        if (validStudent) {
            try {
                reader = PropertiesReader.PropertiesCall("data");
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
        } else {
            try {
                reader = PropertiesReader.PropertiesCall("staffcoursedata");
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String ID = parts[0];
                    String course = parts[1];
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
                reader = PropertiesReader.PropertiesCall("course");
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String course = parts[0];
                    if (!registeredCourses.contains(course)) { continue; }
                    String day = parts[1];
                    String startTime = parts[2];
                    String endTime = parts[3];
                    String[] startTimeParts = startTime.split(":");
                    String[] endTimeParts = endTime.split(":");
                    int startHour = Integer.parseInt(startTimeParts[0]);
                    int endHour = Integer.parseInt(endTimeParts[0]);
                    int startHourIndex = startHour - 8; // (-8) because minimum is 8:00 am; (0, 0) location in the table
                    int endHourIndex = endHour - 8; // (-8) because maximum is 6:00 pm; (11, 0) location in the table
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
                if (occupationType == 2) {
                    System.out.println("[1][TIMETABLE]");
                } else if (menuSelection == 2) {
                    System.out.println("[2][TIMETABLE]");
                }

                // TOP SECTION - 1ST ROW (TIME/DURATION)
                System.out.println("======================================================================================================================================");
                System.out.printf("|%11s|","");
                for (String times : time) {
                    System.out.printf(" %7s%3s", times, "|");
                }
                System.out.println("\n======================================================================================================================================");

                // BOTTOM SECTION - AFTER 1ST ROW (COURSE IN EACH SLOT)
                for (int i = 0; i < DAYS_PER_WEEK; i++) { // LEFTMOST COLUMN (DAY ONLY)
                    System.out.printf("| %9s |",days[i]);
                    for (int j = 0; j < HOURS_PER_DAY; j++) {
                        String courseSlot = timetable[i][j]; // i -> row / j -> column
                        if (courseSlot == null) {
                            courseSlot = "    ";
                        }
                        System.out.printf(" %7s%3s", courseSlot, "|");
                    }
                    System.out.println();
                    System.out.printf("|%11s|","");
                    for (int j = 0; j < HOURS_PER_DAY; j++) { // CLASSROOM
                        String courseSlot = timetable[i][j]; // i -> row / j -> column
                        Classroom classroom = new Classroom();
                        String classroomSlot;
                        if (courseSlot == null) {
                            classroomSlot = "    ";
                        } else {
                            classroomSlot = classroom.getClassroomFromCourse(courseSlot, i);
                        }
                        System.out.printf(" %7s%3s", classroomSlot, "|");
                    }
                    // CLOSE EACH ROW SECTION OF DAY
                    if (i < DAYS_PER_WEEK-1) {
                        System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------");
                    }
                }
                // LAST ROW AFTER EVERY SECTION OF DAYS
                System.out.println("\n--------------------------------------------------------------------------------------------------------------------------------------\n");
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
