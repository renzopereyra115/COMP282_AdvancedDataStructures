import java.util.ArrayList;
import java.util.List;

public class thisNode {

        private thisNode parent;
        private static final int ORDER = 4;
        private int numItems = 0;
        
        private thisNode children[] = new thisNode[ORDER];
        private BoxItem itemArray[] = new BoxItem[ORDER-1];

        public void connectChild(int childNum, thisNode child) 
        {
            children[childNum] = child;
            if(child != null) 
            {
                child.parent = this;
            }
        } 

        public thisNode disconnectChild(int childNum)
        {
            thisNode tempNode = children[childNum];
            children[childNum] = null;
            return tempNode;
        }

        public thisNode getChild(int childNum) 
        {
            if(childNum == -1)
            {
                return null;
            }
            else if(children[childNum] == null) 
            {
                return null;
            }
            return children[childNum];
        }

        public int findChild(double key)
        {
            for(int i = 0; i < children.length; i++)
            {
                if(children[i].findItem(key) == key) 
                {
                    return i;
                }
            }    
            return -1;
        }

        public thisNode getParent() 
        {
            return parent;
        }

        public boolean isLeaf() 
        {
            return (children[0]==null) ? true : false;
        }

        public int getNumItems() 
        {
            if(this == null) 
            {
                return -1;
            }
            return numItems;
        }

        public BoxItem getItem(int index) 
        {
            return itemArray[index];
        }

        public boolean isFull() 
        {
            return (numItems==ORDER-1) ? true : false;
        }

        public boolean isEmpty()
        { 
            return (numItems == 0) ? true : false;
        }

        public double findItem(double key)
        {
            for(int i = 0; i < itemArray.length; i++)
            {
                if(itemArray[i] == null) 
                {
                    break;
                }
                else if(itemArray[i].data == key) 
                {
                    return itemArray[i].data;
                }
            }
            return -1;
        }

        public int findItemIndex(double key)
        {
            for(int i = 0; i < ORDER-1; i++)
                if(itemArray[i]== null)
                {
                    break;
                }
                else if(itemArray[i].data == key) 
                {
                    return i;
                }
            return -1;
        }

        public int insertItem(BoxItem newItem) 
        {
            if(this.isFull()) 
            {
                return -1;
            }

            numItems++;
            double newKey = newItem.data;

            for(int i=numItems-1; i>=0; i--)
            {
                if(itemArray[i] != null)
                {
                    double currKey = itemArray[i].data;
                    if(newKey < currKey) 
                    {
                        itemArray[i+1] = itemArray[i];
                    }
                    else 
                    {
                        itemArray[i+1] = newItem;
                        return i+1;
                    }
                }
            }
            itemArray[0] = newItem;
            return 0;
        }

        public BoxItem removeItem() 
        {
            BoxItem temp = itemArray[numItems-1];
            itemArray[numItems-1] = null;
            numItems--;
            return temp;
        }

        public BoxItem removeItem(int index) 
        {
            BoxItem temp = itemArray[index];
            BoxItem[]tempArray = new BoxItem[ORDER-1];
            int counter = 0;
            for(int i = 0; i < itemArray.length; i++)
            {
                if(i == index)
                {
                    itemArray[index] = null;
                }
                else
                {
                    tempArray[counter] = itemArray[i];
                    counter++;
                }
            }
            itemArray = tempArray;
            numItems--;
            return temp;
        }

        public void displayNode() 
        {
            for(int j=0; j<numItems; j++)
            {    
                itemArray[j].displayItem();
            }
            System.out.println("|");
        }

}
