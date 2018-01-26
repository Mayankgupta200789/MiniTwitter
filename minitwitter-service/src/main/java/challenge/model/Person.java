package challenge.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @Author Mayank Gupta
 * @Date 8/15/17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person {


    private String handle;

    private String name;

    private Integer personId;


    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;

        Person person = (Person) o;

        if (handle != null ? !handle.equals(person.handle) : person.handle != null) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        return personId != null ? personId.equals(person.personId) : person.personId == null;
    }

    @Override
    public int hashCode() {
        int result = handle != null ? handle.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (personId != null ? personId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Person{" +
                "handle='" + handle + '\'' +
                ", name='" + name + '\'' +
                ", personId=" + personId +
                '}';
    }
}
