package com.example.web.annotations;

/**
 * @Auther: 36560
 * @Date: 2019/8/14 :6:57
 * @Description:
 */
@DBTable(name="Member")
public class Member {
    @SQLString(30)
    String firstName;
    @SQLString(30)
    String lastName;
    @SQLInt
    Integer age;
    @SQLString(value=30,constraints = @Constraints(primaryKey = true))
    String handle;
    static int memberCount;

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Integer getAge() {
        return age;
    }

    public String getHandle() {
        return handle;
    }

    @Override
    public String toString() {
        return "Member{" +
                "handle='" + handle + '\'' +
                '}';
    }
}
