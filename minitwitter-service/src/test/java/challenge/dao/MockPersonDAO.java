package challenge.dao;

import challenge.model.Person;

/**
 * @Author Mayank Gupta
 * @Date 8/16/17
 */
public class MockPersonDAO implements PersonDAO {


    private Integer personId;
    private String name;
    private String userName;

    public MockPersonDAO(Integer personId,String name) {
        this.personId = personId;
        this.name = name;
    }

    public MockPersonDAO(Integer personId,String name,String userName) {
        this.personId = personId;
        this.name = name;
        this.userName = userName;
    }

    @Override
    public Person findById(Integer personId) {

        if( personId == null ) {
            return null;
        }

        Person person = getPerson(personId,null);

        return person;
    }

    private Person getPerson(Integer personId,String personUserName) {
        Person person = new Person();
        person.setPersonId(personId);
        person.setName(name);
        person.setHandle(personUserName);
        return person;
    }

    @Override
    public Person findByUserName(String personUserName) {

        if( personUserName == null ) {
            return null;
        }

        getPerson(personId,personUserName);

        return null;
    }
}
