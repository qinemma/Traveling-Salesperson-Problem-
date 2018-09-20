/**
 * This class creates a Tour of Points using a 
 * Linked List implementation.  The points can
 * be inserted into the list using two heuristics.
 * @author Emma Qin, Terry Wang
 * @author Layla Oesper, modified code 09-22-2017
 */

public class Tour {
    /** A helper class that defines a single node for use in a tour.
     * A node consists of a Point, representing the location of that
     * city in the tour, and a pointer to the next Node in the tour.
     */
    private class Node {
        private Point p;
        private Node next;
    
        /** Constructor creates a new Node at the given Point newP
         * with an intital next value of null.
         * @param newP - the point to associate with the Node.
         */
        public Node(Point newP) {
            p = newP;
            next = null;
        }

        /** Constructor creates a new Node at the given Point newP
         * with the specified next node.
         * @param newP - the point to associate with the Node.
         * @param nextNode - the nextNode this node should point to.
         */
        public Node(Point newP, Node nextNode) {
            p = newP;
            next = nextNode;
        }
    
        /**
         * Return the Point associated with this Node. 
         * (Same value can also be accessed from a Node object node
         * using node.p)
         * @return The Point object associated with this node.
         */
        public Point getPoint() {
            return p;
        }
        
        /**
         * Return the next Node associated with this Node. 
         * (Same value can also be accessed from a Node object node
         * using node.next)
         * @return The next Node object linked from this node..
         */
       public Node getNext() {
           return next;
       }
          
    } // End Node class
    

    // Tour class Instance variables
    private Node head;
    private int size; //number of nodes
    
    
    /**
     * Constructor for the Tour class.  By default sets head to null.
     */
    public Tour() {
        head = null;
        size = 0;
    }


    /**
    * Create a string that describes the tour. 
    * @Returns a string that describes the tour 
    */
    
    public String toString() {
        
        Node temp = head;
        String tourInfo = "";
        // Loop through the tour.
        while (temp != null) {
            tourInfo = tourInfo + temp.getPoint().toString() + "\n";
            temp = temp.getNext();
        }
        tourInfo = tourInfo + "end";
        return tourInfo;
    }

    /**
    * 
    * @Returns an int that describes the number of points that are in the tour.
    */

    public int size() {
        return size;
    }

    /**
    * Draw the tour
    */

    public void draw() {
        Node curNode = head;
        head.getPoint().draw();
        // The the line from curNode to the next node
        if (head.getNext() != null) {
            for(int i = 0; i < size - 1; i++) {
                curNode.getPoint().drawTo(curNode.next.getPoint());
                curNode = curNode.next;
                curNode.getPoint().draw();
            }
        }
        // Connect the first node with the last node.
        curNode.getPoint().drawTo(head.getPoint());
    }

    /**
    * This method calculates and returns the distance in the tour
    * @Return the total distance in the tour
    */

    public double distance() {
        Node temp = head;
        double distance = 0;
        // If there is less than two stops in the tour, the distance is 0
        if(size() < 2) {
            return distance;
        }
        while (temp.getNext() != null) {
            // Add the distance from temp to its next stop
            double pieceDistance = temp.getPoint().distanceTo(temp.getNext().getPoint());
            distance = distance + pieceDistance;
            temp = temp.getNext();
        }

        // Add the distance between the first stop and the last stop
        double firstAndLastDis = temp.getPoint().distanceTo(head.getPoint());
        distance = distance + firstAndLastDis;
        return distance;
    }

    /**
    * Inserts p into the tour using the nearest neighbor heuristic. Basically, we will
    * Insert the point after its nearest point.
    * @param a node that describes the inserted point
    */

    public void insertNearest(Point p) {
        double minDist = Double.MAX_VALUE;
        Node nearestNode = null;
        Node temp = head;
        // If the tour is an empty set, insert the point after head.
        if (temp == null) {
            Node insertedNode = new Node(p);
            head = insertedNode;
            size ++;
        }
        else {
            // Loop through the tour and find the nearest node 
            while (temp != null) {
                double pDistance = p.distanceTo(temp.getPoint());
                if (pDistance < minDist) {
                    minDist = pDistance;
                    nearestNode = temp;
                }
                temp = temp.getNext();
            }

        // Insert the node after the nearest node.
        Node insertedNode = new Node(p, nearestNode.getNext());
        nearestNode.next = insertedNode;
        size ++ ;
            
        }
    }
    
    /**
    * Inserts p into the tour using the smalles neighbor heuristic. Basically, we will
    * Insert the point after the point that total distance is smallest after adding this point
    * @param a node that describes the inserted point
    */
    
    public void insertSmallest(Point p) {
        double minDist = Double.MAX_VALUE;
        Node smallestNode = null;
        Node temp = head;
        double currentTotalDistance = distance();
        if ( size < 2 ) { // if size<2, add them directly
            Node insertedNode = new Node(p);
			if ( size == 0 ) { 
				head = insertedNode;
			}
			
			if (size == 1) {
				head.next = insertedNode;
			}
            
            size++;
        }

  

        else { // calculate total distance and add the point that adds up to smallest total distance

            while(temp.next != null) {
                double newDistance = currentTotalDistance - temp.getPoint().distanceTo(temp.getNext().getPoint()) + 
                                    temp.getPoint().distanceTo(p) + p.distanceTo(temp.getNext().getPoint());
                if (newDistance < minDist) {
                    minDist = newDistance;
                    smallestNode = temp;
                }
                temp = temp.next;
            }

            Node insertedNode = new Node(p, smallestNode.getNext());
            smallestNode.next = insertedNode;
            size ++ ;
           
        }
        

    }
        

    
    
   
   
    public static void main(String[] args) {
        /* Use your main() function to test your code as you write it. 
         * This main() will not actually be run once you have the entire
         * Tour class complete, instead you will run the NearestInsertion
         * and SmallestInsertion programs which call the functions in this 
         * class. 
         */
        
        
        //One example test could be the follow (uncomment to run):
        
        Tour tour = new Tour();
        Point p = new Point(0,0);
        tour.insertSmallest(p);
        p = new Point(0,100);
        tour.insertSmallest(p);
        p = new Point(100, 100);
        tour.insertSmallest(p);
        System.out.println(tour.toString());
        System.out.println("Tour distance =  "+tour.distance());
        System.out.println("Number of points = "+tour.size());
        System.out.println(tour);
        
         

        // the tour size should be 3 and the distance 341.42 (don't forget to include the trip back
        // to the original point)
    
        // uncomment the following section to draw the tour, setting w and h to the max x and y 
        // values that occur in your tour points
    
        
        int w = 100 ; //Set this value to the max that x can take on
        int h = 100 ; //Set this value to the max that y can take on
        StdDraw.setCanvasSize(w, h);
        StdDraw.setXscale(0, w);
        StdDraw.setYscale(0, h);
        StdDraw.setPenRadius(.005);
        tour.draw(); 
        
    }


}

   
