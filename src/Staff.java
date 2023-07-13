package src;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

public class Staff extends User
{
    public Staff() {
        super();
    }

    public Staff(String ID) {
        this.ID = ID;
    }

    public Staff(String ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public void setData(String ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void display() {
        System.out.println("Staff ID: " + ID);
        System.out.println("Staff Name: " + name);
    }

    @Override
    public void menu() {
        System.out.println("\n[STAFF MENU]");
        System.out.println("=================================");
        System.out.println("| [1] Add new course            |");
        System.out.println("| [2] View staff's timetable    |");
        System.out.println("| [3] View student's timetable  |");
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
                if (parts[0].equals(ID) && parts[2].equals("1")) {
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