package OOP.ec22906.MP.contributions;

public class Item {
    
    public final String name;
    
    public Item(String nameOfItem) {
        name = nameOfItem;
    }
    
    public boolean equals(Item x) {
        return name.equals(x.name);
    }
    
    public String toString() {return name + "("+this.hashCode()+")";}
}