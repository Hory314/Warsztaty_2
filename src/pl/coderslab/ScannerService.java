package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ScannerService
{
    private String readFile(String pathToFile) //disabled
    {
        try
        {
            File file = new File(pathToFile);
            Scanner scan = new Scanner(file);
            while(scan.hasNextLine()) // poki sa nowe linie w pliku
            {
                String result = scan.nextLine();
                //System.out.println(line);
            }

        }
        catch(NullPointerException | FileNotFoundException ex)
        {
            System.err.println(ex.getMessage());
        }
        return "";
    }

    public static int getInt(String errorMsg)
    {
        Scanner scan = new Scanner(System.in);
        while(!scan.hasNextInt())
        {
            System.out.print(errorMsg);
            scan.next();
        }
        return scan.nextInt();
    }

    public static int getInt()
    {
        return getInt("Proszę wprowadzić liczbę całkowitą: ");
    }

    public static String getString()
    {
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

    public static double getDouble(String errorMsg)
    {
        Scanner scan = new Scanner(System.in);
        while(!scan.hasNextDouble())
        {
            System.out.print(errorMsg);
            scan.next();
        }
        return scan.nextDouble();
    }

    public static double getDouble()
    {
        return getDouble("Proszę podać liczbę zmiennoprzecinkową: ");
    }
}