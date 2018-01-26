package challenge.service.message.context;

/**
 * @Author Mayank Gupta
 * @Date 8/17/17
 */
public class MessageDAOContext {

    private String personId;

    private String keyword;

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
