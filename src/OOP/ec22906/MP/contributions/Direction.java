package OOP.ec22906.MP.contributions;//import java.util.List;

public class Direction {
       
    // Hide constructor so unofficial directions cannot be created.
    private Direction() {};
    
    // Destination's prespective.
    public static final Direction FROM_SOUTH = new Direction();
    public static final Direction FROM_WEST = new Direction();
    public static final Direction FROM_NORTH = new Direction();
    public static final Direction FROM_EAST = new Direction();
    
    //static final List<Direction> LIST_OF_DIRECTIONS = 
    //    List.of(FROM_SOUTH, FROM_WEST, FROM_NORTH, FROM_EAST);
    
    //static final int NUMBER_OF_DIRECTIONS = LIST_OF_DIRECTIONS.size();
     
    // Origin's perspective.
    public static final Direction TO_NORTH = FROM_SOUTH;
    public static final Direction TO_EAST = FROM_WEST;
    public static final Direction TO_SOUTH = FROM_NORTH;
    public static final Direction TO_WEST = FROM_EAST;

    public static final Direction UNDEFINED = new Direction();

    public static Direction opposite(Direction d) {
        
        if (d == FROM_SOUTH) return TO_SOUTH;
        if (d == FROM_EAST)  return TO_EAST;
        if (d == FROM_NORTH) return TO_NORTH;
        if (d == FROM_WEST)  return TO_WEST;
        
        return UNDEFINED;   
    }

    public static Direction turnRight(Direction d) {
        
        if (d == FROM_SOUTH) return TO_EAST;
        if (d == FROM_EAST)  return TO_NORTH;
        if (d == FROM_NORTH) return TO_WEST;
        if (d == FROM_WEST)  return TO_SOUTH;
        
        return UNDEFINED;   
    }

    public static Direction turnLeft(Direction d) {
        
        if (d == FROM_SOUTH) return TO_WEST;
        if (d == FROM_EAST)  return TO_SOUTH;
        if (d == FROM_NORTH) return TO_EAST;
        if (d == FROM_WEST)  return TO_NORTH;
        
        return UNDEFINED;   
    }
    
    public String toString() {
        
        if (this == FROM_SOUTH) return "heading North";
        if (this == FROM_EAST)  return "heading West";
        if (this == FROM_NORTH) return "heading South";
        if (this == FROM_WEST)  return "heading East";
        if (this == UNDEFINED)  return "heading nowhere in particular";
        
        return "[impossible heading]";   
    }
} 
