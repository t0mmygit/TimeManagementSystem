package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.Buffer;

public class RepeaterStudent extends Student {
    private int repeatCount;

    RepeaterStudent(Student student) {
        ID = student.getID();
        name = student.getName();
        semester = student.getSemester();
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }

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
                    setRepeatCount(Integer.parseInt(parts[3]));
                    break;
                }
            }
            reader.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        System.out.println("Repeat Count: " + getRepeatCount());
    }
}
