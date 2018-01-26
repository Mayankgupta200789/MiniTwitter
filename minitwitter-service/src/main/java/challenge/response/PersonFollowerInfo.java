package challenge.response;

import challenge.model.Person;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Map;

/**
 * @Author Mayank Gupta
 * @Date 8/21/17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonFollowerInfo {

    private String personName;

    private Map<String,List<Person>> followers;
    private Map<String,List<Person>> followedByPersons;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Map<String, List<Person>> getFollowers() {
        return followers;
    }

    public void setFollowers(Map<String, List<Person>> followers) {
        this.followers = followers;
    }

    public Map<String, List<Person>> getFollowedByPersons() {
        return followedByPersons;
    }

    public void setFollowedByPersons(Map<String, List<Person>> followedByPersons) {
        this.followedByPersons = followedByPersons;
    }
}
