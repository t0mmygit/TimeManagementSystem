package src;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

public class Course
{
    private String course;
    private String day;
    private String startTime;
    private String endTime;

    public Course() {
        course = null;
        day = null;
        startTime = null;
        endTime = null;
    }

    public Course(String course, String day, String startTime, String endTime) {
        this.course = course;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getCourse() {
        return course;
    }

    public String toString(boolean sentinel) {
        String line;
        if (sentinel) {
            System.out.println("--------------------------------------");
            line = String.format("| %s | %9s | %5s | %5s |", course, day, startTime, endTime);
        } else {
            line = String.format("|        | %9s | %5s | %5s |", day, startTime, endTime);
        }
        return line;
    }

    public void displayCourse(int selection, User std) {
        if (selection == 1) {
            System.out.println("[1][ADD COURSE]");
        } else if (selection == 2) {
            System.out.println("[2][ENROLL COURSE]");
        } else if (selection == 3) {
            System.out.println("[3][DROP COURSE]");
        }
        System.out.println("======================================");
        System.out.println("| COURSE |       DAY | START |   END |");
        BufferedReader reader;
        ArrayList<String> courses = new ArrayList<>();
        ArrayList<String> database = new ArrayList<>();
        try {
            int num = 0;
            int count = 0;
            String line;
            if (selection == 3) {
                reader = PropertiesReader.PropertiesCall("data");
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts[0].equals(std.ID)) {
                        database.add(parts[2]);
                    }
                }
                reader.close();
            }
            reader = PropertiesReader.PropertiesCall("course");
            while ((line = reader.readLine()) != null) {
                count++;
            }
            reader.close();
            Course[] courseArr = new Course[count];
            reader = PropertiesReader.PropertiesCall("course");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                courseArr[num++] = new Course(parts[0], parts[1], parts[2], parts[3]);
            }
            reader.close();
            for (int i = 0; i < count; i++) {
                if (!courses.contains(courseArr[i].getCourse())) {
                    if (selection == 3 && !database.contains(courseArr[i].getCourse())) {
                        continue;
                    }
                    System.out.println(courseArr[i].toString(true));
                    courses.add(courseArr[i].getCourse());
                    for (int j = i+1; j < count; j++) {
                        if (courseArr[j].getCourse().equalsIgnoreCase(courseArr[i].getCourse())) {
                            System.out.println(courseArr[j].toString(false));
                        }
                    }
                }
            }
            System.out.println("--------------------------------------");
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public void addNewCourse() { // Staff
        String FILE_COURSE = "Course.txt";
        Scanner scan = new Scanner(System.in);
        boolean proceed = true;
        do {
            System.out.print("INPUT NEW COURSE: ");
            course = scan.next();
            course = course.toUpperCase();
            int choice = JOptionPane.showConfirmDialog(null, "Are you sure to add the course: " + course + "?", "Confirmation", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.NO_OPTION || choice == JOptionPane.CLOSED_OPTION) {
                break;
            }
            boolean validDay = false;
            while (!validDay) {
                System.out.print("INPUT DAY: ");
                day = scan.next();
                day = day.toUpperCase();
                String[] days = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
                for (String s : days) {
                    if (day.equalsIgnoreCase(s)) {
                        validDay = true;
                        break;
                    }
                }
                if (!validDay) {
                    JOptionPane.showMessageDialog(null,"Invalid input, Please try again!","Alert",JOptionPane.WARNING_MESSAGE);
                }
            }
            while (true) {
                System.out.print("START TIME (HH:MM): ");
                startTime = scan.next();
                if (!startTime.matches("\\d{2}:\\d{2}")) {
                    JOptionPane.showMessageDialog(null,"Incorrect format! Please enter in HH:MM format","Alert",JOptionPane.WARNING_MESSAGE);
                    continue;
                }
                String[] splitStartTime = startTime.split(":");
                int startHour;
                try {
                    startHour = Integer.parseInt(splitStartTime[0]);
                } catch (NumberFormatException NFE) {
                    JOptionPane.showMessageDialog(null,"Incorrect input! Please enter a valid numeric value","Alert",JOptionPane.WARNING_MESSAGE);
                    continue;
                }
                if (startHour < 8) {
                    JOptionPane.showMessageDialog(null,"The minimum allowed is 8:00 a.m.!","Alert",JOptionPane.WARNING_MESSAGE);
                } else if (startHour > 17) {
                    JOptionPane.showMessageDialog(null,"The maximum allowed is 5:00 p.m.!","Alert",JOptionPane.WARNING_MESSAGE);
                } else {
                    break;
                }
            }
            while (true) {
                System.out.print("END TIME (HH:MM): ");
                endTime = scan.next();
                if (!endTime.matches("\\d{2}:\\d{2}")) {
                    JOptionPane.showMessageDialog(null,"Incorrect format! Please enter in HH:MM format","Alert",JOptionPane.WARNING_MESSAGE);
                    continue;
                }
                String[] splitStartTime = endTime.split(":");
                int endHour;
                try {
                    endHour = Integer.parseInt(splitStartTime[0]);
                } catch (NumberFormatException NFE) {
                    JOptionPane.showMessageDialog(null,"Incorrect input! Please enter a valid numeric value","Alert",JOptionPane.WARNING_MESSAGE);
                    continue;
                }
                if (endHour < 9) {
                    JOptionPane.showMessageDialog(null,"The minimum allowed is 9:00 a.m.!","Alert",JOptionPane.WARNING_MESSAGE);
                } else if (endHour > 18) {
                    JOptionPane.showMessageDialog(null,"The maximum allowed is 6:00 p.m.!","Alert",JOptionPane.WARNING_MESSAGE);
                } else {
                    break;
                }
            }
            BufferedReader reader;
            BufferedWriter writer;
            boolean courseExist = false;
            try {
                reader = PropertiesReader.PropertiesCall("course");
                String line;
                while ((line = reader.readLine()) != null) { // Check for similar course and its element
                    String[] parts = line.split(",");
                    if (parts[0].equals(course)) {
                        if (parts[1].equals(day) && parts[2].equals(startTime) && parts[3].equals(endTime)) {
                            courseExist = true;
                            break;
                        }
                    }
                }
                reader.close();
                if (!courseExist) { // Write the input data into Course.txt
                    writer = new BufferedWriter(new FileWriter(FILE_COURSE, true));
                    writer.write(course + "," + day + "," + startTime + "," + endTime);
                    writer.newLine();
                    writer.close();
                } else {
                    JOptionPane.showMessageDialog(null, "Course already existed!", "Alert", JOptionPane.WARNING_MESSAGE);
                }
                ArrayList<String> courseList = new ArrayList<>(); // Arrange courseList by day - start from Monday
                reader = new BufferedReader(new FileReader("Course.txt"));
                while ((line = reader.readLine()) != null) {
                    courseList.add(line);
                }
                for (String course : courseList) {
                    System.out.println(course);
                }
                reader.close();
                Map<String, Integer> dayMap = new HashMap<>();
                dayMap.put("MONDAY", 0);
                dayMap.put("TUESDAY", 1);
                dayMap.put("WEDNESDAY", 2);
                dayMap.put("THURSDAY", 3);
                dayMap.put("FRIDAY", 4);
                dayMap.put("SATURDAY", 5);
                dayMap.put("SUNDAY", 6);
                courseList.sort((course1, course2) -> {
                    String[] courseArr1 = course1.split(",");
                    String[] courseArr2 = course2.split(",");
                    int day1 = dayMap.get(courseArr1[1]);
                    int day2 = dayMap.get(courseArr2[1]);
                    return Integer.compare(day1, day2);
                });
                writer = new BufferedWriter(new FileWriter(FILE_COURSE)); // Write the arranged data into Course.txt
                for (String course : courseList) {
                    writer.write(course);
                    writer.newLine();
                }
                for (String course : courseList) {
                    System.out.println(course);
                }
                writer.close();
                proceed = false;
                if (!proceed) {
                    JOptionPane.showMessageDialog(null, "Course [" + course + "] has successfully added!", "Successful", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } while (proceed);
    }

    public void enrollCourse(Student student) { // Student: REGISTER FOR COURSES
        String FILE_DATABASE = "data/database.txt";
        String ID = student.getID();
        String name = student.getName();
        BufferedReader reader;
        BufferedWriter writer;
        Scanner scan = new Scanner(System.in);
        try {
            displayCourse(2, student);
            boolean repeatCourse = true;
            boolean courseFound;
            do {
                int courseCount = 0;
                ArrayList<String> courseSet = new ArrayList<>();
                ArrayList<String> databaseSet = new ArrayList<>();
                ArrayList<String> databaseOfID = new ArrayList<>();
                ArrayList<String> convertDatabaseToArray = new ArrayList<>();
                Set<String> registeredDatabase = new HashSet<>();
                Map<String, Integer> dayMap = new HashMap<>();
                dayMap.put("MONDAY", 0);
                dayMap.put("TUESDAY", 1);
                dayMap.put("WEDNESDAY", 2);
                dayMap.put("THURSDAY", 3);
                dayMap.put("FRIDAY", 4);
                dayMap.put("SATURDAY", 5);
                dayMap.put("SUNDAY", 6);
                reader = PropertiesReader.PropertiesCall("course");
                String line = reader.readLine();
                while (line != null) {
                    courseSet.add(line);
                    line = reader.readLine();
                }
                reader.close();
                reader = PropertiesReader.PropertiesCall("data");
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String studentID = parts[0];
                    if (studentID.equals(ID)) {
                        databaseOfID.add(line);
                        courseCount++;
                    }
                    databaseSet.add(line);
                }
                databaseSet.sort((line1, line2) -> {
                    String[] parts1 = line1.split(",");
                    String[] parts2 = line2.split(",");
                    return parts1[0].compareTo(parts2[0]);
                });
                writer = new BufferedWriter(new FileWriter(FILE_DATABASE));
                for (String database : databaseSet) {
                    writer.write(database);
                    writer.newLine();
                }
                writer.close();
                for (String course : courseSet) {
                    String[] courseArr = course.split(",");
                    for (String database : databaseOfID) {
                        String[] databaseArr = database.split(",");
                        if (databaseArr[2].equals(courseArr[0])) {
                            registeredDatabase.add(course);
                        }
                    }
                }
                convertDatabaseToArray.addAll(registeredDatabase);
                convertDatabaseToArray.sort((course1, course2) -> {
                    String[] course1Arr = course1.split(",");
                    String[] course2Arr = course2.split(",");
                    int day1 = dayMap.get(course1Arr[1]);
                    int day2 = dayMap.get(course2Arr[1]);
                    return Integer.compare(day1, day2);
                });
                reader.close();
                if (courseCount == courseSet.size()) {
                    JOptionPane.showMessageDialog(null,"You've registered for all available courses!","Information",JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
                courseFound = false;
                System.out.print("CHOOSE A COURSE: ");
                String course = scan.nextLine();
                course = course.toUpperCase();
                reader = PropertiesReader.PropertiesCall("course");
                line = reader.readLine();
                while(line != null) {
                    String[] parts = line.split(",");
                    if(parts[0].equals(course)) {
                        courseFound = true;
                    }
                    line = reader.readLine();
                }
                reader.close();
                if (courseFound) {
                    reader = PropertiesReader.PropertiesCall("data");
                    line = reader.readLine();
                    String newRecord = (ID + "," + name + "," + course);
                    boolean databaseEmpty = false;

                    int count = 0;
                    if (line == null || line.equals("")) {
                        databaseEmpty = true;
                    } else {
                        while (line != null) {
                            String[] parts = line.split(",");
                            if (parts[0].equals(ID)) {
                                line = reader.readLine();
                                count++;
                            } else {
                                line = reader.readLine();
                            }
                        }
                    }
                    reader.close();

                    reader = PropertiesReader.PropertiesCall("data");
                    line = reader.readLine();
                    if (count == 0 || databaseEmpty) {
                        writer = new BufferedWriter(new FileWriter(FILE_DATABASE, true));
                        writer.write(newRecord);
                        writer.newLine();
                        writer.close();
                        JOptionPane.showMessageDialog(null,"Course registered successfully!","Information",JOptionPane.INFORMATION_MESSAGE);
                        repeatCourse = false;
                    } else {
                        do {
                            String[] parts = line.split(",");
                            if (parts[0].equals(ID)) {
                                count--;
                                if (parts[2].equals(course)) {
                                    JOptionPane.showMessageDialog(null,"You've already registered this course!","Alert",JOptionPane.WARNING_MESSAGE);
                                    repeatCourse = false;
                                    break;
                                } else if (count == 0) {
                                    boolean timeClash = false;
                                    String courseDetails = null;
                                    for (String courses : courseSet) {
                                        if (courses.startsWith(course)) {
                                            courseDetails = courses;
                                            String[] courseArray = courseDetails.split(",");
                                            day = courseArray[1];
                                            startTime = courseArray[2];
                                            endTime = courseArray[3];
                                            break;
                                        }
                                    }
                                    int databaseCount = 1;
                                    for (String database : convertDatabaseToArray) {
                                        String[] databaseArray = database.split(",");
                                        if (databaseArray[1].equals(day)) {
                                            if ((startTime.compareTo(databaseArray[2]) >= 0 && startTime.compareTo(databaseArray[3]) < 0) ||
                                                    (endTime.compareTo(databaseArray[2]) > 0 && endTime.compareTo(databaseArray[3]) <= 0) ||
                                                    (startTime.compareTo(databaseArray[2]) <= 0 && endTime.compareTo(databaseArray[3]) >= 0)) {
                                                JOptionPane.showMessageDialog(null," Course " + course + ", time clashes with the existing registered courses!","Alert",JOptionPane.WARNING_MESSAGE);
                                                repeatCourse = false;
                                                timeClash = true;
                                                break;
                                            }
                                        }
                                    }
                                    if (!timeClash) {
                                        writer = new BufferedWriter(new FileWriter(FILE_DATABASE, true));
                                        writer.write(newRecord);
                                        writer.newLine();
                                        JOptionPane.showMessageDialog(null,"Course registered successfully!","Information",JOptionPane.INFORMATION_MESSAGE);
                                        writer.close();
                                        repeatCourse = false;
                                    }
                                } else {
                                    line = reader.readLine();
                                }
                            } else {
                                line = reader.readLine();
                            }
                        } while (count != 0);
                        reader.close();
                    }
                } else {
                    JOptionPane.showMessageDialog(null,"Invalid Input!","Alert",JOptionPane.WARNING_MESSAGE);
                    repeatCourse = false;
                }
            } while (repeatCourse);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public boolean dropCourse(Student student) { // STUDENT
        String FILE_DATABASE = "data/database.txt";
        Scanner scan = new Scanner(System.in);
        ArrayList<String> database = new ArrayList<>();
        boolean dropCourse = true;
        boolean repeatDropCourse = false;
        do {
            repeatDropCourse = false;
            BufferedReader reader;
            try {
                reader = PropertiesReader.PropertiesCall("data");
                String line = reader.readLine();
                while (line != null) {
                    String[] parts = line.split(",");
                    if (parts[0].equals(student.ID)) {
                        database.add(parts[0]);
                    }
                    line = reader.readLine();
                }
                reader.close();
                if (database.size() == 0) {
                    JOptionPane.showMessageDialog(null,"You've yet register a course!\nPlease enroll a course first!","Alert",JOptionPane.WARNING_MESSAGE);
                    dropCourse = false;
                    break;
                } else {
                    database.clear();
                    reader = new BufferedReader(new FileReader(FILE_DATABASE));
                    while ((line = reader.readLine()) != null) {
                        database.add(line);
                    }
                }
                reader.close();
                displayCourse(3, student);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            boolean successfulDrop = false;
            System.out.print("DROP A COURSE: ");
            String course = scan.nextLine();
            course = course.toUpperCase();
            for (int i = 0; i < database.size(); i++) {
                String[] parts = database.get(i).split(",");
                if (parts[0].equals(student.ID) && course.equals(parts[2])) {
                    database.remove(i);
                    JOptionPane.showMessageDialog(null,"Course dropped successfully!","Information",JOptionPane.INFORMATION_MESSAGE);
                    successfulDrop = true;
                    break;
                } else if (i == (database.size()-1)) {
                    JOptionPane.showMessageDialog(null,"Course not found or you haven't registered for the course!","Alert",JOptionPane.WARNING_MESSAGE);
                }
            } try {
                if (successfulDrop) {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_DATABASE, false));
                    for (String overwriteDatabase : database) {
                        writer.write(overwriteDatabase);
                        writer.newLine();
                    }
                    writer.flush();
                    writer.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } while (repeatDropCourse);
        return dropCourse;
    }
}