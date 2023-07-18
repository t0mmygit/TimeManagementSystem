package src;

import java.io.BufferedReader;
import java.io.IOException;

public class DistanceStudyStudent extends Student {
    private String studyLocation;

    DistanceStudyStudent(Student student) {
        ID = student.getID();
        name = student.getName();
        semester = student.getSemester();
    }

    public String getStudyLocation() { return studyLocation; }

    public void setStudyLocation(String studyLocation) { this.studyLocation = studyLocation; }

    public void displayDetails() {
        System.out.println("Student ID: " + getID());
        System.out.println("Student Name: " + getName());
        System.out.println("Semester: " + getSemester());
        try {
            String line = null;
            BufferedReader reader = PropertiesReader.PropertiesCall("studentdata");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts[0].equals(ID)) {
                    setStudyLocation(parts[3]);
                    break;
                }
            }
            reader.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.println("Study Location: " + getStudyLocation());
    }
}
