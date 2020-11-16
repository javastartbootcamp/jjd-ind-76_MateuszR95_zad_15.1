package pl.javastart.task;

import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

@Timeout(5)
public class SortingTest {

    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    private final TournamentStats tournamentStats = new TournamentStats();
    private final File statsFile = new File("stats.csv");

    @Test
    @DisplayName("Przykład z opisu zadania")
    void shouldWorkForExampleInput() throws IOException {
        // given
        provideInput("Jan Kowalski 425", "Maria Zawadzka 234", "STOP", "1", "2");
        statsFile.delete();
        Scanner scanner = new Scanner(System.in);

        // when
        tournamentStats.run(scanner);

        // then
        assertThat(statsFile)
                .as("Plik stats.csv nie istnieje!")
                .exists();
        List<String> lines = Files.readAllLines(statsFile.toPath());
        assertThat(lines)
                .as("Plik nie zawiera wpisów w odpowiedniej kolejności.")
                .containsSubsequence("Maria Zawadzka;234", "Jan Kowalski;425");
    }

    @Test
    @DisplayName("Sortowanie po imieniu rosnąco")
    void shouldSortByNameAsc() throws IOException {
        // given
        provideInput("Adam Zaręba 555", "Barbara Woźniak 333", "Cezary Abacki 777", "STOP", "1", "1");
        statsFile.delete();
        Scanner scanner = new Scanner(System.in);

        // when
        tournamentStats.run(scanner);

        // then
        assertThat(statsFile)
                .as("Plik stats.csv nie istnieje!")
                .exists();
        List<String> lines = Files.readAllLines(statsFile.toPath());
        assertThat(lines)
                .as("Plik nie zawiera wpisów w odpowiedniej kolejności.")
                .containsSubsequence("Adam Zaręba;555", "Barbara Woźniak;333", "Cezary Abacki;777");
    }

    @Test
    @DisplayName("Sortowanie po imieniu malejąco")
    void shouldSortByNameDesc() throws IOException {
        // given
        provideInput("Adam Zaręba 555", "Barbara Woźniak 333", "Cezary Abacki 777", "STOP", "1", "2");
        statsFile.delete();
        Scanner scanner = new Scanner(System.in);

        // when
        tournamentStats.run(scanner);

        // then
        assertThat(statsFile)
                .as("Plik stats.csv nie istnieje!")
                .exists();
        List<String> lines = Files.readAllLines(statsFile.toPath());
        assertThat(lines)
                .as("Plik nie zawiera wpisów w odpowiedniej kolejności.")
                .containsSubsequence("Cezary Abacki;777", "Barbara Woźniak;333", "Adam Zaręba;555");
    }

    @Test
    @DisplayName("Sortowanie po nazwisku rosnąco")
    void shouldSortBySurnameAsc() throws IOException {
        // given
        provideInput("Adam Zaręba 555", "Barbara Woźniak 333", "Cezary Abacki 777", "STOP", "2", "1");
        statsFile.delete();
        Scanner scanner = new Scanner(System.in);

        // when
        tournamentStats.run(scanner);

        // then
        assertThat(statsFile)
                .as("Plik stats.csv nie istnieje!")
                .exists();
        List<String> lines = Files.readAllLines(statsFile.toPath());
        assertThat(lines)
                .as("Plik nie zawiera wpisów w odpowiedniej kolejności.")
                .containsSubsequence("Cezary Abacki;777", "Barbara Woźniak;333", "Adam Zaręba;555");
    }

    @Test
    @DisplayName("Sortowanie po nazwisku malejąco")
    void shouldSortBySurnameDesc() throws IOException {
        // given
        provideInput("Adam Zaręba 555", "Barbara Woźniak 333", "Cezary Abacki 777", "STOP", "2", "2");
        statsFile.delete();
        Scanner scanner = new Scanner(System.in);

        // when
        tournamentStats.run(scanner);

        // then
        assertThat(statsFile)
                .as("Plik stats.csv nie istnieje!")
                .exists();
        List<String> lines = Files.readAllLines(statsFile.toPath());
        assertThat(lines)
                .as("Plik nie zawiera wpisów w odpowiedniej kolejności.")
                .containsSubsequence("Adam Zaręba;555", "Barbara Woźniak;333", "Cezary Abacki;777");
    }

    @Test
    @DisplayName("Sortowanie po punktach rosnąco")
    void shouldSortByPointsAsc() throws IOException {
        // given
        provideInput("Adam Zaręba 555", "Barbara Woźniak 333", "Cezary Abacki 777", "STOP", "3", "1");
        statsFile.delete();
        Scanner scanner = new Scanner(System.in);

        // when
        tournamentStats.run(scanner);

        // then
        assertThat(statsFile)
                .as("Plik stats.csv nie istnieje!")
                .exists();
        List<String> lines = Files.readAllLines(statsFile.toPath());
        assertThat(lines)
                .as("Plik nie zawiera wpisów w odpowiedniej kolejności.")
                .containsSubsequence("Barbara Woźniak;333", "Adam Zaręba;555", "Cezary Abacki;777");
    }

    @Test
    @DisplayName("Sortowanie po punktach malejąco")
    void shouldSortByPointsDesc() throws IOException {
        // given
        provideInput("Adam Zaręba 555", "Barbara Woźniak 333", "Cezary Abacki 777", "STOP", "3", "2");
        statsFile.delete();
        Scanner scanner = new Scanner(System.in);

        // when
        tournamentStats.run(scanner);

        // then
        assertThat(statsFile)
                .as("Plik stats.csv nie istnieje!")
                .exists();
        List<String> lines = Files.readAllLines(statsFile.toPath());
        assertThat(lines)
                .as("Plik nie zawiera wpisów w odpowiedniej kolejności.")
                .containsSubsequence("Cezary Abacki;777", "Adam Zaręba;555", "Barbara Woźniak;333");
    }

    @BeforeEach
    void init() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void cleanup() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    private void provideInput(String... lines) {
        String input = String.join("\r\n", lines);
        input += "\r\n";

        ByteArrayInputStream testIn = new ByteArrayInputStream(input.getBytes());
        System.setIn(testIn);
    }

}
