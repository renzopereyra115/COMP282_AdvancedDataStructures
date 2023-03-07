package Java.COMP282_AdvancedDataStructures.Project2;

//creates class for constructing AVL tree
class buildTree
{
	private Node rootNode;

	//Constructor, sets null value to rootNode
	public buildTree()
	{
		rootNode = null;
	}

	//method to wipe AVL Tree empty
	public void removeAll()
	{
		rootNode = null;
	}

	//method, checks whether AVL Tree is empty or not
	public boolean isEmpty()
	{
		if (rootNode == null)
		{
			return true;
		}
		else 
		{
			return false;
		}
	}

	//method, inserts an element to AVL Tree
	public void insertElement(int element)
	{
		rootNode = insertElement(element, rootNode);
	}

	//method, gets height of AVL Tree
	private int getHeight(Node node)
	{
		//tests whether "node == null" is true
		//if true, return "-1", if false return value in "node.h"
		return node == null ? -1 : node.h;
	}

	//get max height from left & right node
	private int getMaxHeight(int leftNodeHeight, int rightNodeHeight)
	{
		//if statement true, return "leftNodeHeight" value
		//if statement false, return "rightNodeHeight" value
		return leftNodeHeight > rightNodeHeight ? leftNodeHeight : rightNodeHeight;
	}

	//method, inserts data in AVL Tree
	private Node insertElement(int element, Node node)
	{
		if (node == null)
		{
			node = new Node(element);
		}
		//inserts a node when given element is less than root node element
		else if (element < node.element)
		{
			node.leftChild = insertElement(element, node.leftChild);
			if(getHeight(node.leftChild) - getHeight(node.rightChild) == 2)
			{
				if(element < node.leftChild.element)
				{
					node = rotateWithLeftChild(node);
				}
				else 
				{
					node = doubleWithLeftChild(node);
				}
			}
		}
		else if (element > node.element)
		{
			node.rightChild = insertElement(element, node.rightChild);
			if (getHeight(node.rightChild) - getHeight(node.leftChild)==2)
			{
				if (element>node.rightChild.element)
				{
					node = rotateWithRightChild(node);
				}
				else
				{
					node = doubleWithRightChild(node);
				}
			}
		}
		else
		{
			//if element already in tree, do nothing
			node.h = getMaxHeight(getHeight(node.leftChild),getHeight(node.rightChild))+1;
		}
		return node;
	}

	//method, perform rotation of binary tree node w/ left child
	private Node rotateWithLeftChild(Node node2)
	{
		Node node1 = node2.leftChild;
		node2.leftChild = node1.rightChild;
		node1.rightChild = node2;
		node2.h = getMaxHeight(getHeight(node2.leftChild), getHeight(node2.rightChild))+1;
		node1.h = getMaxHeight(getHeight(node1.leftChild),node2.h)+1;
		return node1;
	}

	//method, perform rotation of binary tree node w/ right child
	private Node rotateWithRightChild(Node node1)
	{
		Node node2 = node1.rightChild;
		node1.rightChild = node2.leftChild;
		node2.leftChild = node1;
		node1.h = getMaxHeight(getHeight(node1.leftChild), getHeight(node1.rightChild))+1;
		node2.h = getMaxHeight(getHeight(node2.rightChild), node1.h)+1;
		return node2;
	}

	//method, perform double rotation of binary tree node
	//1st rotates left w/ right child, then node3 w/ new left child
	private Node doubleWithLeftChild(Node node3)
	{
		node3.leftChild = rotateWithRightChild(node3.leftChild);
		return rotateWithLeftChild(node3);
	}

	//method, perform double rotation of binary tree node
	//1st rotates right w/ left child, then node1 w/ new right child
	private Node doubleWithRightChild(Node node1)
	{
		node1.rightChild = rotateWithLeftChild(node1.rightChild);
		return rotateWithRightChild(node1);
	}

	//method, gets total # of nodes in AVL Tree
	public int getTotalNumberOfNodes()
	{
		return getTotalNumberOfNodes(rootNode);
	}
	private int getTotalNumberOfNodes(Node head)
	{
		if (head == null)
		{
			return 0;
		}
		else
		{
			int length = 1;
			length = length + getTotalNumberOfNodes(head.leftChild);
			length = length + getTotalNumberOfNodes(head.rightChild);
			return length;
		}
	}

	//method, finds an element in AVL Tree
	public boolean searchElement(int element)
	{
		return searchElement(rootNode, element);
	}
	private boolean searchElement(Node head, int element)
	{
		boolean check = false;
		while ((head != null) && !check)
		{
			int headElement = head.element;
			if (element < headElement)
			{
				head = head.leftChild;
			}
			else if (element > headElement)
			{
				head = head.rightChild;
			}
			else 
			{
				check = true; 
				break; 
			}
			check = searchElement(head, element);
		}
		return check;
	}

	//method, traverses through AVL Tree in "in-order" form
	public void inorderTraversal()
	{
		inorderTraversal(rootNode);
	}
	private void inorderTraversal(Node head)
	{
		if (head != null)
		{
			inorderTraversal(head.leftChild);
			System.out.print(head.element+" ");
			inorderTraversal(head.rightChild);
		}
	}

	//method, traverses through AVL Tree in "pre-order" form
	public void preorderTraversal()
	{
		preorderTraversal(rootNode);
	}
	private void preorderTraversal(Node head)
	{
		if (head != null)
		{
			System.out.print(head.element+" ");
			preorderTraversal(head.leftChild);
			preorderTraversal(head.rightChild);
		}
	}

	//method, traverses AVL Tree in "post-order" form
	public void postorderTraversal()
	{
		postorderTraversal(rootNode);
	}
	private void postorderTraversal(Node head)
	{
		if (head != null)
		{
			postorderTraversal(head.leftChild);
			postorderTraversal(head.rightChild);
			System.out.print(head.element+" ");
		}
	}
}