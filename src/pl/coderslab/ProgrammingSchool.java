package pl.coderslab;

import java.util.List;

public class ProgrammingSchool
{
    public static void main(String[] args)
    {
        Users user = new Users();
        user.setUsername("dasfsadsadffsaf");
        user.setPassword("dsasdafdfsadfsdafsaf");
        user.setEmail("dsaasfsadfsadfsdaff");
        //user.setUserGroupId("dsaasfsdaff");
        UsersDao.create(user);
//        printUsersTable();
//        scanForOperation();
    }

    private static void scanForOperation()
    {
        while(true)
        {
            System.out.print("Podaj operację: ");
            String line = ScannerService.getString();
            if(line.equals("add"))
            {
                addOperation();
                System.out.println("Zapisano pomyślnie.");
            }
            else if(line.equals("quit"))
            {
                break;
            }
        }
    }

    private static void addOperation()
    {
        System.out.print("Podaj nazwę użytkownika: ");
        String username = ScannerService.getString();
        System.out.print("Podaj e-mail użytkownika: ");
        String email = ScannerService.getString();
        System.out.print("Podaj hasło użytkownika: ");
        String password = ScannerService.getString();
        System.out.print("Podaj ID grupy użytkownika: ");
        String userGroupId = ScannerService.getString();

        Users users = new Users();
        try // jak sie uda parsowac to wykonaj to i finally
        {
            int userGroupIdInt = Integer.parseInt(userGroupId);
            users.setUserGroupId(userGroupIdInt);
        }
        catch(NumberFormatException e) // jak nie udalo sie parsowac to wykonaj tylko finally
        {
        }
        finally
        {
            users.setUsername(username);
            users.setEmail(email);
            users.setPassword(password);
        }


        UsersDao.create(users);
    }

    private static void printUsersTable()
    {
        List<Users> userGroupList = UsersDao.selectAll();
        for(Users el : userGroupList)
        {
            StringBuilder sb = new StringBuilder();

            sb.append(el.getId()).append(" | ");
            sb.append(el.getUsername()).append(" | ");
            sb.append(el.getEmail()).append(" | ");
            sb.append(el.getPassword()).append(" | ");
            sb.append(el.getUserGroupId());

            System.out.println(sb.toString());
        }
    }
}
