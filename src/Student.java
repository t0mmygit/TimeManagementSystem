package src;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Student extends User
{
    public Student() {
        super();
    }

    public Student(String ID) {
        this.ID = ID;
    }

    public void setData(String ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public String getID () {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void display() {
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
    public boolean verificationID()
    {
        boolean verificationStatus = false;
        boolean foundID = false;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("userDatabase.txt"));
            int lineCount = 0;
            String line = reader.readLine();
            while (line != null) {
                lineCount++;
                line = reader.readLine();
            }
            reader.close();
            int count = 0;
            reader = new BufferedReader(new FileReader("userDatabase.txt"));
            line = reader.readLine();
            while (count < lineCount && !foundID) {
                String[] parts = line.split(",");
                if (parts[0].equals(ID) && parts[2].equals("2")) {
                    setData(parts[0], parts[1]);
                    foundID = true;
                    verificationStatus = true;
                } else {
                    count++;
                    line = reader.readLine();
                }
            }
            reader.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return verificationStatus;
    }
}