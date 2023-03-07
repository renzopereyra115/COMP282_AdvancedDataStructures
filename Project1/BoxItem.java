public class BoxItem 
{
    //This represents the block item
    public double data;
    public double getKey()
    {
        return this.data;
    }
    public BoxItem(double d) 
    {
        this.data = d;
    }
    public void displayItem()
    {
        System.out.print("|" + data);
    }
}
