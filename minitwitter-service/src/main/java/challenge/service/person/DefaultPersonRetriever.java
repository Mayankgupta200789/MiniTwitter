package challenge.service.person;

import challenge.dao.PersonDAO;
import challenge.model.Person;

/**
 * @Author Mayank Gupta
 * @Date 8/16/17
 */
public class DefaultPersonRetriever implements PersonRetriever {

    private PersonDAO personDAO;


    public DefaultPersonRetriever(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public Person retrieve(Integer personId) {

        return personDAO.findById(personId);
    }
}
