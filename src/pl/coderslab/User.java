package pl.coderslab;

import org.mindrot.jbcrypt.BCrypt;

public class User
{
    private int id;
    private String username;
    private String email;
    private String password;
    private Integer userGroupId;

    public User()
    {
    }

    public User(String username, String email, String password, int userGroupId)
    {
        this.username = username;
        this.email = email;
        this.password = hashPassword(password); // ustawiaj zahashowane
        this.userGroupId = userGroupId;
    }

    private static String hashPassword(String password)
    {
        return BCrypt.hashpw(password, BCrypt.gensalt()); // ma zwracac zahashowane haslo
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
        return password; // pobiera oczywiscie zahashowane bo takie bedzie trzymane tylko w obiekcie
    }

    public void setHashedPassword(String password)
    {
        this.password = hashPassword(password); // ustawiaj zahashowane
    }

    public void setPasswordNoHashing(String password)
    {
        this.password = password;
    }


    public Integer getUserGroupId()
    {
        return userGroupId;
    }

    public void setUserGroupId(Integer userGroupId)
    {
        this.userGroupId = userGroupId;
    }

    @Override
    public String toString()
    {
        return "User{" + "id=" + id + ", username='" + username + '\'' + ", email='" + email + '\'' + ", password='" + password + '\'' + ", userGroupId=" + userGroupId + '}';
    }
}
