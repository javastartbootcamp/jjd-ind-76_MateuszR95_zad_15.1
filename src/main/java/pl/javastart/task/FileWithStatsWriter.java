package pl.javastart.task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileWithStatsWriter {
    public static void writeListToFile(List<Person> participants, String fileName) throws IOException {
        try (FileWriter fileWriter = new FileWriter(fileName);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)
        ) {
            for (Person participant : participants) {
                bufferedWriter.write(participant.toCsv());
                bufferedWriter.newLine();
            }
        }
    }

}
