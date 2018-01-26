package challenge.dao;

import challenge.model.Person;

/**
 *
 * This interface is responsible to
 * retrieve the person details for
 * a given person id and username
 *
 * @Author Mayank Gupta
 * @Date 8/15/17
 */
public interface PersonDAO {


    /**
     * This method is responsible to find a person's
     * information based on personId
     *
     * @param personId
     * @return
     */
    Person findById(Integer personId);

    /**
     * This method is responsible to find person's
     * information based on person username
     *
     * @param personUserName
     * @return
     */
    Person findByUserName(String personUserName);
}
