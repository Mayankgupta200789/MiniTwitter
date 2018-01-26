package challenge.service.message.context;

/**
 * @Author Mayank Gupta
 * @Date 8/17/17
 */
public class MessageAccumulatorContext {

    private Integer personId;

    private String keyword;

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
