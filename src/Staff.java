package src;

import java.io.BufferedReader;
import java.io.IOException;

public class Staff extends User {
    private String position;
    public Staff() {
        super();
    }

    public Staff(String ID) {
        this.ID = ID;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setData(String ID, String name) {
        this.ID = ID;
        this.name = name;
    }

    public String getID() {
        return ID;
    }
    public void setStaffID(String ID) { this.ID = ID; }

    public String getName() {
        return name;
    }

    public void displayDetails() {
        System.out.println("Staff ID: " + ID);
        System.out.println("Staff Name: " + name);
        try {
            String line;
            BufferedReader reader = PropertiesReader.PropertiesCall("staffdata");
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts[0].equals(ID)) {
                    setPosition(parts[2]);
                    break;
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        System.out.println("Staff Position: " + getPosition());
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
        boolean verificationStatus = false, foundID = false;
        try {
            BufferedReader reader = PropertiesReader.PropertiesCall("userdata");
            int lineCount = 0;
            String line = reader.readLine();
            while (line != null) {
                lineCount++;
                line = reader.readLine();
            }
            reader.close();
            int count = 0;
            reader = PropertiesReader.PropertiesCall("userdata");
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