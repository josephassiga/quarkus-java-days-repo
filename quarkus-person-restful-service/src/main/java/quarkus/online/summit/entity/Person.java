package quarkus.online.summit.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
public class Person extends PanacheEntity {

    // The person's name
    public String name;

    // The person's birthdate
    public LocalDate birth;

    // The person's eye color
    @Column(length = 8)
    public String eyes;

    public static List<Person> findByColor(final String color) {
        return list("eyes", color);
    }

    public static List<Person> getBeforeYear(final int year) {
        return Person.<Person>streamAll()
                .filter(p -> p.birth.getYear() <= year)
                .collect(Collectors.toList());
    }
}
