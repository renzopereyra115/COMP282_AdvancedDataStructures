//Name: Renzo Pereyra
//Course: COMP282
//Assignment: Project 1

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class buildTree 
{
    public static int getInt() throws IOException 
    {
        String s = getString();
        return Integer.parseInt(s);
    }
    public static char getChar() throws IOException 
    {
        String s = getString();
        return s.charAt(0);
    }
    public static String getString() throws IOException 
    {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        String s = br.readLine();
        return s;
    }
    public static void main(String[] args) throws IOException 
    {
        double value;
        Tree myTree = new Tree();
        boolean active = true;

        while(active)
        {    
            System.out.print("Options:\n");
            System.out.print("#1. Print Your Tree\n");
            System.out.print("#2. Insert a Value\n");
            System.out.print("#3. Remove a Value\n");
            System.out.print("#4. Find a Value\n");
            System.out.println();
            System.out.print("#0. Quit Program\n");
            char choice = getChar();
            switch(choice) 
            {
                case '1':
                    myTree.displayTree();
                    break;
                case '2':
                    System.out.print("Enter Value: ");
                    value = getInt();
                    myTree.insert(value);
                    break;
                case '4':
                    System.out.print("Find Value: ");
                    value = getInt();
                    thisNode found = myTree.find(value);
                    if (found != null)
                    {
                        System.out.println("Value " + value + " Found.");
                    }
                    else
                    {
                        System.out.println("Value "+ value + " Does Not Exist.");
                    }
                    break;
                case '3':
                    System.out.print("Enter Deletion Value: ");
                    value = getInt();
                    myTree.remove(value);
                    break;
                case '0':
                    active = false;
                    break;
                default:
                    System.out.print("Invalid Entry\n");
            } 
        } 
    } 
}
