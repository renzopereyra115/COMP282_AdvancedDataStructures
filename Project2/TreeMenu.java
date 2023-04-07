package Java.COMP282_AdvancedDataStructures.Project2;

import java.util.Scanner;

public class TreeMenu 
{
	public static void main(String [] args)
	{
		//Scanner class gets input from user
		Scanner in = new Scanner (System.in);

		//creates object of buildTree
		buildTree obj = new buildTree();

		Boolean resume = true;

		
		


		//create main menu
		do
		{
			System.out.println("Please choose an option:");
			System.out.println("1. Insert a node");
			System.out.println("2. Delete a node");
			System.out.println("3. Search a node");
			System.out.println("4. Remove all nodes from AVL Tree");
			System.out.println("5. Display AVL Tree in Post-Order");
			System.out.println("6. Display AVL Tree in Pre-Order");
			System.out.println("7. Display AVL Tree in In-Order");
			
			System.out.println("0. Exit Program");
			System.out.println();
			System.out.print("User Input: ");

			//get user input
			int userChoice = in.nextInt();
			switch(userChoice)
			{
				case 1:
					System.out.println("Please enter an element to insert in AVL Tree: ");
					obj.insertElement(in.nextInt());
					break;
				case 2:
					System.out.println("Please enter an element to remove from AVL Tree: ");
					obj.deleteElement(in.nextInt());
					break;
				case 3:
					System.out.println("Enter integer element to search: ");
					System.out.println(obj.searchElement(in.nextInt()));
					break;
				case 4:
					obj.removeAll();
					System.out.println("Tree Cleared Successfully!");
					break;
				case 5:
					System.out.println("Traversing AVL Tree in Post-Order...");
					obj.postorderTraversal();
					System.out.println();
					break;
				case 6:
					System.out.println("Traversing AVL Tree in Pre-Order...");
					obj.preorderTraversal();
					System.out.println();
					break;
				case 7:
					System.out.println("Traversing AVL Tree in In-Order...");
					obj.inorderTraversal();
					System.out.println();
					break;
				case 0:
					//exits program
					resume = false;	
					System.out.println("Exiting program...");
					break;
				default:
					//do nothing
					System.out.println();
					break;
			}
			System.out.println();
		}
		while (resume == true); //ends do-while loop
		System.out.println("Program has exited. Thank you!");
		in.close();
	}
}