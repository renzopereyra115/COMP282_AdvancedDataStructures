public class Tree 
{
    private thisNode root = new thisNode();

    public thisNode find(double key)
    {
        return binary_Search(root,key);
    }
    public thisNode binary_Search(thisNode currentNode, double key)
    {
        if(currentNode.isEmpty()) return null;
        if(currentNode.findItem(key) == key) 
        {
            return currentNode;
        }

        if(currentNode.findItem(key) != key && !currentNode.isLeaf()) 
        {
            int numItems = currentNode.getNumItems();
            for (int i = 0; i < numItems; i++) 
            {
                if(key < currentNode.getItem(0).data) 
                {
                    return binary_Search(currentNode.getChild(0), key);
                }
                else if(key > currentNode.getItem(numItems-1).data)
                {
                    return binary_Search(currentNode.getChild(numItems), key);
                }
                else if (i != 3 && currentNode.getItem(i).data < key && currentNode.getItem(i+1).data > key)
                {
                    return binary_Search(currentNode.getChild(i+1),key);
                }
            }
        }
        return null;
    }

    public thisNode getNextChild(thisNode theNode, double data) 
    {
        int k;
        for(k = 0; k < theNode.getNumItems(); k++)
            if(data < theNode.getItem(k).getKey()) return theNode.getChild(k);
        return theNode.getChild(k);
    }
    public void insert(double data) 
    {
        thisNode currentNode = root;
        BoxItem tempItem = new BoxItem(data);
        while(true) 
        {
            if(currentNode.isFull()) 
            {
                split(currentNode);
                currentNode = currentNode.getParent();
                currentNode = getNextChild(currentNode, data);
            }
            else if(currentNode.isLeaf()) 
            {
                break;
            }
            else 
            {
                currentNode = getNextChild(currentNode, data);
            }
        }
        currentNode.insertItem(tempItem);
    }

    public void split(thisNode thisNode)
    {
        BoxItem itemC, itemB;
        thisNode parent, child1, child2;
        int itemIndex;

        itemC = thisNode.removeItem();
        itemB = thisNode.removeItem();
        child1 = thisNode.disconnectChild(2);
        child2 = thisNode.disconnectChild(3);

        thisNode newRight = new thisNode();

        if(thisNode==root) 
        {
            root = new thisNode();
            parent = root;
            root.connectChild(0, thisNode);
        }
        else parent = thisNode.getParent();

        itemIndex = parent.insertItem(itemB);

        for(int i = parent.getNumItems()-1; i > itemIndex; i--)
        {
            thisNode temp = parent.disconnectChild(i);
            parent.connectChild(i+1, temp);
        }
        parent.connectChild(itemIndex+1, newRight);

        newRight.insertItem(itemC);
        newRight.connectChild(0, child1);
        newRight.connectChild(1, child2);
    }

    public void remove(double key)
    {
        thisNode currentNode = find(key);
        if(currentNode == null) 
        {
            System.out.println("No data to remove!");
            return;
        }
        thisNode parent = currentNode.getParent();
        if(!currentNode.isLeaf())
        {
            int curIndex = currentNode.findItemIndex(key);
            int indexKey = parent.findChild(key);
            if(currentNode.getChild(curIndex).getNumItems() > 1)
            {
                thisNode leftTemp = currentNode.getChild(curIndex);
                BoxItem temp = leftTemp.getItem(leftTemp.getNumItems()-1);
                currentNode.getChild(curIndex).removeItem(currentNode.getChild(curIndex).getNumItems());
                currentNode.removeItem(curIndex);
                currentNode.insertItem(temp);
            }
            else if(currentNode.getChild(curIndex+1).getNumItems()>1)
            {
                BoxItem temp = currentNode.getChild(curIndex).getItem(0);
                currentNode.removeItem(curIndex);
                currentNode.insertItem(temp);
            }
            else pullFromParent(parent,currentNode,curIndex,indexKey);
            thisNode leftChild = currentNode.getChild(0);
            if(currentNode.getItem(0).data < leftChild.getItem(0).data)
            {
                BoxItem curNodeTemp = currentNode.getItem(0);
                BoxItem leftChildTemp = leftChild.getItem(0);
                currentNode.removeItem(0);
                currentNode.insertItem(leftChildTemp);
                currentNode.getChild(0).removeItem(leftChild.getNumItems());
                currentNode.getChild(0).insertItem(curNodeTemp);
            }
            checkForEmptyChild(currentNode);
        }
        else
        {
            int indexKey = parent.findChild(key);
            int leftKey = indexKey - 1;
            int rightKey = indexKey + 1;
            int curIndex = currentNode.findItemIndex(key);
            if(currentNode.getNumItems() > 1) 
            {
                currentNode.removeItem(curIndex);
            }

            else if(indexKey != -1 && (leftKey != -1 || rightKey != currentNode.getParent().getNumItems() + 1))
            {
                if(indexKey == 0 && parent.getChild(rightKey).getNumItems() > 1 ) 
                {
                    leftRotation(currentNode,parent,indexKey);
                }
                else if(parent.getChild(leftKey) != null && parent.getChild(leftKey).getNumItems() > 1) 
                {
                    rightRotation(currentNode,parent,indexKey);
                }
                else if(parent.getNumItems() > 1)
                {
                    thisNode rightSibling = parent.getChild(rightKey);
                    merge(currentNode,parent,rightSibling,indexKey);
                }
                else
                {
                    thisNode temp = parent;
                    thisNode rightSibling = temp.getChild(1);
                    parent.removeItem();
                    parent.insertItem(currentNode.getItem(0));
                    parent.insertItem(temp.getItem(0));
                    parent.insertItem(rightSibling.getItem(0));
                    rightSibling.removeItem();
                    parent.disconnectChild(1);
                    currentNode.removeItem();
                    parent.disconnectChild(0);
                    this.remove(key);
                    checkForEmptyChild(parent);
                }
            }
        }
    }
    public void merge(thisNode nodeLeft, thisNode nodeParent, thisNode nodeRight,int index)
    {
        thisNode mainNode  = nodeLeft;
        BoxItem parentTemp = nodeParent.getItem(index);
        BoxItem rightTemp = nodeRight.getItem(0);
        mainNode.insertItem(parentTemp);
        mainNode.insertItem(rightTemp);
        nodeRight.removeItem();
        nodeParent.removeItem(index);
        nodeParent.disconnectChild(index + 1);
        mainNode.removeItem(0);
        checkForEmptyChild(nodeParent);
    }
    public void rightRotation(thisNode currentNode, thisNode parent, int indexKey)
    {
        int leftKey = indexKey - 1;
        thisNode leftSibling = parent.getChild(leftKey);
        BoxItem leftTemp = leftSibling.removeItem(leftSibling.getNumItems() - 1);
        BoxItem parentTemp = parent.removeItem(indexKey-1);
        currentNode.removeItem();
        currentNode.insertItem(parentTemp);
        parent.insertItem(leftTemp);
        checkForEmptyChild(parent);
    }
    public void leftRotation(thisNode currentNode, thisNode parent, int indexKey)
    {
        int rightKey = indexKey + 1;
        thisNode rightSibling = parent.getChild(rightKey);
        BoxItem rightTemp = rightSibling.getItem(0);
        BoxItem parentTemp = parent.removeItem(indexKey);
        currentNode.removeItem(0);
        currentNode.insertItem(parentTemp);
        parent.insertItem(rightTemp);
        parent.getChild(rightKey).removeItem(0);
        checkForEmptyChild(parent);
    }
    public void checkForEmptyChild(thisNode currentNode)
    {
        for(int b = 0; b < currentNode.getNumItems()+1; b++)
        {
            if(currentNode.getChild(b) == null)
            {
                int leftIndex = b - 1;
                int rightIndex = b + 1;
                if(rightIndex >= 4)
                {
                    if(currentNode.getChild(leftIndex).getNumItems() > 1)
                    {
                        BoxItem temp = currentNode.getItem(b);
                        currentNode.removeItem(b);
                        BoxItem childTemp = currentNode.getChild(leftIndex).getItem(currentNode.getChild(leftIndex).getNumItems());
                        currentNode.getChild(leftIndex).removeItem(currentNode.getChild(leftIndex).getNumItems());
                        currentNode.getChild(b).insertItem(temp);
                        currentNode.insertItem(childTemp);
                    }
                    else if (currentNode.getNumItems() > 1)
                    {
                        BoxItem temp = currentNode.getItem(b);
                        currentNode.getChild(leftIndex).insertItem(temp);
                        currentNode.disconnectChild(b);
                        currentNode.removeItem(b);
                    }
                    else
                    {
                        BoxItem temp = currentNode.getChild(leftIndex).getItem(0);
                        currentNode.removeItem();
                        currentNode.insertItem(temp);
                        currentNode.disconnectChild(0);
                    }
                    checkForEmptyChild(currentNode);
                }
                else
                {
                    if(currentNode.getChild(rightIndex).getNumItems() > 1)
                    {
                        currentNode.connectChild(b,currentNode.getChild(rightIndex));
                        currentNode.disconnectChild(rightIndex);
                    }
                    else if (currentNode.getNumItems()>1)
                    {
                        BoxItem temp = currentNode.getItem(b);
                        currentNode.getChild(rightIndex).insertItem(temp);
                        currentNode.disconnectChild(b);
                        currentNode.removeItem(b);
                        checkForEmptyChild(currentNode);
                    }
                    else
                    {
                        BoxItem temp = currentNode.getChild(rightIndex).getItem(0);
                        currentNode.getChild(rightIndex).removeItem();
                        try 
                        {
                            currentNode.getChild(b).insertItem(temp);
                        } 
                        catch(NullPointerException e)
                        {
                            currentNode.getChild(rightIndex).insertItem(temp);
                            currentNode.connectChild(b,currentNode.getChild(rightIndex));
                        }
                        currentNode.disconnectChild(rightIndex);
                    }
                }
            }
        }
    }

    public void pullFromParent(thisNode parent, thisNode currentNode, int indexToDelete, int indexKey)
    {
        BoxItem targetItem;
        if(parent.getItem(0).data > currentNode.getItem(indexToDelete).data)
        {
            targetItem = parent.getItem(0);
            thisNode temp = parent.getChild(0);
            BoxItem sucessor = temp.getItem(0);

            while(!temp.isLeaf())
            {
                temp = temp.getChild(0);
                sucessor = temp.getItem(0);
            }
            parent.removeItem(0);
            parent.insertItem(sucessor);
            parent.getChild(indexKey).removeItem(indexToDelete);
            parent.getChild(indexKey).insertItem(targetItem);
        }
        else
        {
            targetItem = parent.getItem(indexKey-1);
            thisNode temp = parent.getChild(indexKey-1);
            BoxItem sucessor = temp.getItem(temp.getNumItems()-1);
            while(!temp.isLeaf())
            {
                if(temp.getChild(temp.getNumItems() + 1) != null) temp = temp.getChild(temp.getNumItems() + 1);
                else temp = temp.getChild(temp.getNumItems());
                sucessor = temp.getItem(temp.getNumItems()-1);
            }
            parent.removeItem(indexKey-1);
            parent.insertItem(sucessor);
            parent.getChild(indexKey).removeItem(indexToDelete);
            parent.getChild(indexKey).insertItem(targetItem);
        }

    }

    public void displayTree() 
    {
        recDisplayTree(root, 0, 0);
    }

    private void recDisplayTree(thisNode thisNode, int level, int childNumber) 
    {
        System.out.print("level="+level+" child="+childNumber+" ");
        thisNode.displayNode();

        int numItems = thisNode.getNumItems();
        for(int j=0; j<numItems+1; j++)
        {
            thisNode nextNode = thisNode.getChild(j);
            if(nextNode != null) 
            {
                recDisplayTree(nextNode, level+1, j);
            }
            else return;
        }
    }
}
