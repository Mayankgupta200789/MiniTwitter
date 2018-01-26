package challenge.service.person;

import challenge.model.Person;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author Mayank Gupta
 * @Date 8/16/17
 */
public class MockPersonRetriever implements PersonRetriever {

    private  Set<Integer> validPersonIds = new HashSet<>();

    private String personName;

    public MockPersonRetriever(Integer personId, String personName) {
        validPersonIds.add(personId);
        this.personName = personName;

    }

    @Override
    public Person retrieve(Integer personId) {

        if(!validPersonIds.contains(personId)) {
            return null;
        }

        Person person = new Person();
        person.setName(personName);
        person.setPersonId(personId);
        return person;
    }
}
