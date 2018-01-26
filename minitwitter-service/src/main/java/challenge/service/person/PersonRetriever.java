package challenge.service.person;

import challenge.model.Person;

/**
 *
 * This interface is responsible
 * to provide person details based on
 * the personId
 * @Author Mayank Gupta
 * @Date 8/15/17
 */
public interface PersonRetriever {


    /**
     * This method is responsible to retrieve the list of persons
     * based on a given person id
     *
     * @param personId
     * @return
     */
    Person retrieve(Integer personId);
}
