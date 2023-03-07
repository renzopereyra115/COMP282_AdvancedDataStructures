package Java.COMP282_AdvancedDataStructures.Project2;

class Node
{
	int element;
	int h; //h for height
	Node leftChild;
	Node rightChild; 

	public Node() //creates a null node
	{
		leftChild = null; //declares left child null
		rightChild = null; //declares right child null
		element = 0; // initializes element to zero
		h = 0; //initializes height to zero
	}

	public Node(int element) //constructor
	{
		leftChild = null;
		rightChild = null;
		this.element = element; //???
		h = 0;
	}
}