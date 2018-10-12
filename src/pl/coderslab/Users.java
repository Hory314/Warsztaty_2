package pl.coderslab;

public class Users
{
    private int id;
    private String username;
    private String email;
    private String password;
    private Integer userGroupId;

    public Users()
    {
    }

    public Users(String username, String email, String password, int userGroupId)
    {
        this.username = username;
        this.email = email;
        this.password = password;
        this.userGroupId = userGroupId;
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Integer getUserGroupId()
    {
        return userGroupId;
    }

    public void setUserGroupId(int userGroupId)
    {
        this.userGroupId = userGroupId;
    }

    @Override
    public String toString()
    {
        return "Users{" + "id=" + id + ", username='" + username + '\'' + ", email='" + email + '\'' + ", password='" + password + '\'' + ", userGroupId=" + userGroupId + '}';
    }
}
