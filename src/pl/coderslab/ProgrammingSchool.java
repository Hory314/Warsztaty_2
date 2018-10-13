package pl.coderslab;

import java.util.List;

public class ProgrammingSchool
{
    public static void main(String[] args)
    {
        printUsersTable();
        scanForOperation();
    }

    private static void scanForOperation()
    {
        scanner:
        while(true)
        {
            System.out.print("Podaj operację: ");
            String line = ScannerService.getString();
            switch(line)
            {
                case "add":
                    addOperation();
                    break;
                case "edit":
                    editOperation();
                    break;
                case "delete":
                    deleteOperation();
                    break;
                case "update":
                    updateOperation();
                    break;
                case "show":
                    showOperation();
                    break;
                case "quit":
                    break scanner;
            }
        }
    }

    private static void showOperation()
    {
        System.out.print("Podaj ID użytkownika: ");
        int id = ScannerService.getInt();
        if(UserDao.exist(id))
        {
            User user = UserDao.select(id);
            StringBuilder sb = new StringBuilder();

            sb.append(user.getId()).append(" | ");
            sb.append(user.getUsername()).append(" | ");
            sb.append(user.getEmail()).append(" | ");
            sb.append(user.getPassword()).append(" | ");
            sb.append(user.getUserGroupId());

            System.out.println(sb.toString());
        }
        else
        {
            System.out.println("Nie ma takiego użytkownika.");
        }
    }

    private static void updateOperation()
    {
        System.out.print("Podaj ID użytkownika: ");
        int id = ScannerService.getInt();
        if(UserDao.exist(id))
        {
            System.out.print("Podaj nazwę użytkownika: ");
            String username = ScannerService.getString();
            System.out.print("Podaj e-mail użytkownika: ");
            String email = ScannerService.getString();
            System.out.print("Podaj hasło użytkownika: ");
            String password = ScannerService.getString();
            System.out.print("Podaj ID grupy użytkownika: ");
            String userGroupId = ScannerService.getString();

            User user = new User();
            try // jak sie uda parsowac to wykonaj to i finally
            {
                int userGroupIdInt = Integer.parseInt(userGroupId);
                System.out.println("udalo sie parsowac str na int");
                user.setUserGroupId(userGroupIdInt);
            }
            catch(NumberFormatException e) // jak nie udalo sie parsowac to wykonaj tylko finally
            {
            }
            finally
            {
                user.setId(id);
                user.setUsername(username);
                user.setEmail(email);
                user.setPassword(password);
            }

            UserDao.update(user);
        }
        else
        {
            System.out.println("Nie ma takiego użytkownika.");
        }

    }

    private static void deleteOperation()
    {
        System.out.print("Podaj ID użytkownika: ");
        int id = ScannerService.getInt();
        UserDao.delete(id);
    }

    private static void editOperation()
    {
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

        User user = new User();
        try // jak sie uda parsowac to wykonaj to i finally
        {
            int userGroupIdInt = Integer.parseInt(userGroupId);
            user.setUserGroupId(userGroupIdInt);
        }
        catch(NumberFormatException e) // jak nie udalo sie parsowac to wykonaj tylko finally
        {
        }
        finally
        {
            user.setUsername(username);
            user.setEmail(email);
            user.setPassword(password);
        }

        UserDao.create(user);
    }

    private static void printUsersTable()
    {
        List<User> userGroupList = UserDao.selectAll();
        for(User el : userGroupList)
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
