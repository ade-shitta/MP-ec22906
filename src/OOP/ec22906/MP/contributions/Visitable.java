package OOP.ec22906.MP.contributions;

public interface Visitable {
    
    Direction visit( // Returns direction the visitor leaves towards.
                     Visitor visitor,
                     Direction directionVistorArrivesFrom);
}