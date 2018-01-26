package challenge.service.command.context;

/**
 * @Author Mayank Gupta
 * @Date 8/17/17
 */
public class CommandContext {

    private Integer personId;

    private String followerUsername;

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getFollowerUsername() {
        return followerUsername;
    }

    public void setFollowerUsername(String followerUsername) {
        this.followerUsername = followerUsername;
    }
}
