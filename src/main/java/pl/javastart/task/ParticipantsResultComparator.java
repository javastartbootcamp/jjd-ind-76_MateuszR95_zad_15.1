package pl.javastart.task;

import java.util.Comparator;

public class ParticipantsResultComparator implements Comparator<Person> {
    @Override
    public int compare(Person p1, Person p2) {
        return Integer.compare(p1.getResult(), p2.getResult());
    }
}
