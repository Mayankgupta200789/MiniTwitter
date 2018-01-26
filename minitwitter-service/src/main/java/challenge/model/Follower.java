package challenge.model;

/**
 * @Author Mayank Gupta
 * @Date 8/15/17
 */
public class Follower {

    private int followerId;

    private String peopleId;

    private String followerPeopleId;

    public int getFollowerId() {
        return followerId;
    }

    public void setFollowerId(int followerId) {
        this.followerId = followerId;
    }

    public String getPeopleId() {
        return peopleId;
    }

    public void setPeopleId(String peopleId) {
        this.peopleId = peopleId;
    }

    public String getFollowerPeopleId() {
        return followerPeopleId;
    }

    public void setFollowerPeopleId(String followerPeopleId) {
        this.followerPeopleId = followerPeopleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Follower)) return false;

        Follower follower = (Follower) o;

        if (followerId != follower.followerId) return false;
        if (peopleId != null ? !peopleId.equals(follower.peopleId) : follower.peopleId != null) return false;
        return followerPeopleId != null ? followerPeopleId.equals(follower.followerPeopleId) : follower.followerPeopleId == null;
    }

    @Override
    public int hashCode() {
        int result = followerId;
        result = 31 * result + (peopleId != null ? peopleId.hashCode() : 0);
        result = 31 * result + (followerPeopleId != null ? followerPeopleId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Follower{" +
                "followerId=" + followerId +
                ", peopleId='" + peopleId + '\'' +
                ", followerPeopleId='" + followerPeopleId + '\'' +
                '}';
    }
}
