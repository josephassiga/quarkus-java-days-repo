package quarkus.online.summit.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import quarkus.online.summit.entity.Person;

@ApplicationScoped
public class PersonService {

    public List<Person> list() {
        return Person.listAll();
    }

    public Person create(final Person person) {
        person.persist();
        return person;
    }

    public Boolean delete(final UUID uuid) {
        return Person.deleteById(uuid);
    }

    public void update(final Person person) {
        final Map<String, Object> params = new HashMap<>();
        params.put("name", person.name);
        params.put("birth", person.birth);
        params.put("eyes", person.eyes);

        Person.update(
                "update person p set p.name = :name, p.amount = :birth, p.eyes = :eyes and where p.id = :id",
                params);
    }
}
