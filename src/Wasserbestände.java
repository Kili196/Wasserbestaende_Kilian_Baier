
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created: 22.11.2021
 *
 * @author Kilian Baier (20190608)
 */
public class Wasserbestände {

    private Map<LocalDateTime, Integer> levels = new TreeMap();


    public Map<LocalDateTime, Integer> readFromFile(String filename) throws FileNotFoundException {
        int count = 1;
        Map<LocalDateTime, Integer> returnwert = new TreeMap<>();
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                if (count <= 6) {
                    String data = myReader.nextLine();
                    count++;
                } else {
                    String data = myReader.nextLine();
                    String[] str = data.split(" ");
                    String fulldate = str[0] + " " + str[1];
                    String pattern = "dd.MM.yyyy HH:mm";
                    DateTimeFormatter df = DateTimeFormatter.ofPattern(pattern);
                    LocalDateTime dateTime = LocalDateTime.parse(fulldate, df);

                    returnwert.put(LocalDateTime.from(dateTime), Integer.parseInt(str[4]));
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
        levels = returnwert;
        return returnwert;
    }


    public Map<LocalDate, Integer> highest(LocalDateTime from, LocalDateTime to) {
        Map<LocalDate, Integer> newMap = new TreeMap<>();
        int count = 0;
        String rightdate = "";
        for (var i : levels.entrySet()) {
            if (i.getKey().isAfter(from) || i.getKey().isBefore(to)) {
                if (i.getValue() > count) {
                    count = i.getValue();
                    rightdate = String.valueOf(i.getKey());
                }
            }
        }
        newMap.put(LocalDate.parse("dd.MM.yyyy HH:mm", DateTimeFormatter.ofPattern(rightdate)), count);
        return newMap;
    }

    public double average(LocalDateTime from, LocalDateTime to) {
        Map<LocalDate, Integer> newMap = new TreeMap<>();
        int count = 0;
        int countaverage = 0;

        for (var i : levels.entrySet()) {
            if (i.getKey().isAfter(from) || i.getKey().isBefore(to)) {
                count ++;
                countaverage += i.getValue();
            }
        }
        return countaverage/count;
    }

    public static LocalDateTime parseDate(String date){
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd.MM.y HH:mm"));
    }



}
