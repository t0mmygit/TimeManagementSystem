package src;

import java.io.BufferedReader;
import java.io.IOException;

public class Classroom {
    private String classroomCode;

    public String getClassCode() {
        return classroomCode;
    }

    public void setClassCode(String classroomCode) {
        this.classroomCode = classroomCode;
    }

    public String getClassroomFromCourse(String course, int dayIndex) {
        String day = null;
        switch (dayIndex) {
            case 0 -> day = "MONDAY";
            case 1 -> day = "TUESDAY";
            case 2 -> day = "WEDNESDAY";
            case 3 -> day = "THURSDAY";
            case 4 -> day = "FRIDAY";
            case 5 -> day = "SATURDAY";
            case 6 -> day = "SUNDAY";
            default -> System.out.println("Invalid Day!");
        }

        BufferedReader reader = PropertiesReader.PropertiesCall("course");
        try {
            String line;
            while((line = reader.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts[0].equalsIgnoreCase(course)) {
                    if (parts[1].equalsIgnoreCase(day)) {
                        setClassCode(parts[4]);
                        break;
                    }
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return getClassCode();
    }
}
