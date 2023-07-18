package src;

import java.io.BufferedReader;
import java.io.IOException;

public class NormalStudent extends Student {
    private boolean isFullTime;

    NormalStudent(Student student) {
        ID = student.getID();
        name = student.getName();
        semester = student.getSemester();
    }

    public boolean isFullTime() { return isFullTime; }
    public void setFullTime(boolean isFullTime) { this.isFullTime = isFullTime; }

    public void displayDetails() {
        String[] studentDurationType = { "FULL-TIME", "PART-TIME" };
        System.out.println("Student ID: " + getID());
        System.out.println("Student Name: " + getName());
        System.out.println("Semester: " + getSemester());
        try {
            String line = null;
            BufferedReader reader = PropertiesReader.PropertiesCall("studentdata");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts[0].equals(ID)) {
                    setFullTime(parts[3].equalsIgnoreCase(studentDurationType[0]));
                    break;
                }
            }
            reader.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        if (isFullTime()) {
            System.out.println("Full-Time Student");
        } else {
            System.out.println("Part-Time Student");
        }
    }
}
