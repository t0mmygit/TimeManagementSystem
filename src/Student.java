package src;

import java.io.BufferedReader;
import java.io.IOException;

public class Student extends User {
    protected int semester;
    protected StudentType studentType;
    public Student() {
        super();
    }

    public Student(String ID) {
        this.ID = ID;
    }
    public Student(String ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getID () {
        return ID;
    }

    public void setStudentID(String studentID) { this.ID = studentID; }

    public String getName() {
        return name;
    }

    public void setStudentName(String studentName) { this.name = studentName; }

    public StudentType getStudentType() { return studentType; }

    public void setStudentType(StudentType studentType) { this.studentType = studentType; }

    public void displayDetails() {
        System.out.println("Student ID: " + ID);
        System.out.println("Student Name: " + name);
    }

    @Override
    public void menu() {
        System.out.println("\n[STUDENT MENU]");
        System.out.println("=================================");
        System.out.println("| [1] Display timetable         |");
        System.out.println("| [2] Enroll new courses        |");
        System.out.println("| [3] Drop courses              |");
        System.out.println("| [4] Logout                    |");
        System.out.println("=================================\n");
    }

    @Override
    public boolean verificationID() {
        boolean verificationStatus = false;

        try {
            BufferedReader reader = PropertiesReader.PropertiesCall("studentdata");

            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts[0].equals(ID)) {
                    setStudentID(parts[0]);
                    setStudentName(parts[1]);
                    StudentType studentTypePart = StudentType.valueOf(parts[2]);
                    setStudentType(studentTypePart);
                    setSemester(Integer.parseInt(parts[4]));

                    verificationStatus = true;
                }
            }
            reader.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return verificationStatus;
    }
}