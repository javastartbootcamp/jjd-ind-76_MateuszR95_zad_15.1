package pl.javastart.task;

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
    private Scanner scanner = new Scanner(System.in);
    private String fileName = "stats.csv";

    void run(Scanner scanner) {
        mainLoop();
        printSet();
    }

    private void mainLoop() {
        boolean continueLoop = true;
        do {
            String inputText = readTextFromUser();
            if (inputText.equalsIgnoreCase(STOP)) {
                try {
                    sortCollection();
                    FileWithStatsWriter.writeListToFile(participants, fileName);
                    continueLoop = false;
                } catch (IOException e) {
                    System.out.println("Nie udało się zapisać statystyk do pliku");
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }

            } else {
                Person person = createPerson(inputText);
                participants.add(person);
            }

        } while (continueLoop);
    }

    private String readTextFromUser() {
        System.out.println("Podaj wynik kolejnego gracza (lub stop):");
        return scanner.nextLine();
    }

    private Person createPerson(String inputLine) {
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

    private int readSortingParameterOption() {
        System.out.println("Po jakim parametrze posortować? (" + SORTING_BY_FIRST_NAME + " - imię, " +
                SORTING_BY_LAST_NAME +  " - nazwisko, " + SORTING_BY_RESULT + " - wynik)");
        int option = scanner.nextInt();
        scanner.nextLine();
        return option;
    }

    private int readSortingOrder() {
        System.out.println("Sortować rosnąco czy malejąco? (" + ASCENDING_SORTING + " - rosnąco, "
                + DESCENDING_SORTING + " - malejąco)");
        int order = scanner.nextInt();
        scanner.nextLine();
        return order;
    }

    private void sortCollection() {

        int sortingParameterOption = readSortingParameterOption();
        int sortingOrder = readSortingOrder();
        Comparator<Person> comparator = switch (sortingParameterOption) {
            case SORTING_BY_FIRST_NAME -> new ParticipantsFirstNameComparator();
            case SORTING_BY_LAST_NAME -> new ParticipantsLastNameComparator();
            case SORTING_BY_RESULT -> new ParticipantsResultComparator();
            default -> throw new IllegalArgumentException("Nieprawidłowy parametr sortowania");
        };
        if (sortingOrder == DESCENDING_SORTING) {
            comparator = comparator.reversed();
        }
        participants.sort(comparator);
    }
}
