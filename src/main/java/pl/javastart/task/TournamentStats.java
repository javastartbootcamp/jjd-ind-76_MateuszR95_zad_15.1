package pl.javastart.task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class TournamentStats {

    private static final String STOP = "Stop";
    private static final int SORTING_BY_FIRST_NAME = 1;
    private static final int SORTING_BY_LAST_NAME = 2;
    private static final int SORTING_BY_RESULT = 3;
    private static final int ASCENDING_SORTING = 1;
    private static final int DESCENDING_SORTING = 2;
    private List<Person> participants = new ArrayList<>();

    private String fileName = "stats.csv";

    void run(Scanner scanner) {
        mainLoop(scanner);
        printSet();
    }

    public void mainLoop(Scanner scanner) {
        while (true) {
            String inputText = readTextFromUser(scanner);
            if (inputText.equalsIgnoreCase(STOP)) {
                sortCollection(scanner);
                try {
                    writeListToFile(fileName);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return;
            } else {
                Person person = createPerson(inputText);
                participants.add(person);
            }
        }
    }

    public String readTextFromUser(Scanner scanner) {
        System.out.println("Podaj wynik kolejnego gracza (lub stop):");
        return scanner.nextLine();
    }

    public Person createPerson(String inputLine) {
        String[] inputLineArray = inputLine.split(" ");
        String firstName = inputLineArray[0];
        String lastName = inputLineArray[1];
        int result = Integer.parseInt(inputLineArray[2]);
        return new Person(firstName, lastName, result);
    }

    public void printSet() {
        for (Person participant : participants) {
            System.out.println(participant);
        }
    }

    public int readSortingParameterOption(Scanner scanner) {
        System.out.println("Po jakim parametrze posortować? (1 - imię, 2 - nazwisko, 3 - wynik)");
        return scanner.nextInt();
    }

    public int readSortingMethod(Scanner scanner) {
        System.out.println("Sortować rosnąco czy malejąco? (1 - rosnąco, 2 - malejąco)");
        return scanner.nextInt();
    }

    public void sortCollection(Scanner scanner) {
        int sortingParameterOption = readSortingParameterOption(scanner);
        int sortingMethod = readSortingMethod(scanner);
        Comparator<Person> comparator = null;
        switch (sortingParameterOption) {
            case SORTING_BY_FIRST_NAME -> comparator = new ParticipantsFirstNameComparator();
            case SORTING_BY_LAST_NAME -> comparator = new ParticipantsLastNameComparator();
            case SORTING_BY_RESULT -> comparator = new ParticipantsResultComparator();
            default -> System.out.println("Wybrałeś zły typ sortowania");
        }
        if (comparator != null) {
            sortCollectionByMethod(sortingMethod, comparator);
        }
    }

    public void sortCollectionByMethod(int sortingMethodOption, Comparator<Person> comparator) {
        participants.sort(comparator);
        if (sortingMethodOption == DESCENDING_SORTING) {
            Collections.reverse(participants);
        }
    }

    public void writeListToFile(String fileName) throws IOException {
        try (FileWriter fileWriter = new FileWriter(fileName);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        ) {
            for (Person participant : participants) {
                bufferedWriter.write(writePersonToCsv(participant));
                bufferedWriter.newLine();
            }
        }

    }

    public String writePersonToCsv(Person participant) {
        return participant.getFirstName() + " " + participant.getLastName() + ";" + participant.getResult();

    }

}
