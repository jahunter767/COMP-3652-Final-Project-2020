import java.io.*; 
import java.util.*;

  
// Java program to implement 
// a Singly Linked List 
public class LinkedList { 
  
    Node head; // head of list 
  
    // Linked list Node. 
    // This inner class is made static 
    // so that main() can access it 
    public class Node { 
  
        SMPLExp data; 
        Node next; 
  
        // Constructor 
        Node(SMPLExp d) 
        { 
            data = d; 
            next = null; 
        } 
    } 
      
    // Method to insert a new node 
    public void insert(SMPLExp data) 
    { 
        // Create a new node with given data 
        Node new_node = new Node(data); 
        new_node.next = null; 
   
        // If the Linked List is empty, 
        // then make the new node as head 
        if (this.head == null) { 
            this.head = new_node; 
        } 
        else { 
            // Else traverse till the last node 
            // and insert the new_node there 
            Node last = this.head; 
            while (last.next != null) { 
                last = last.next; 
            } 
   
            // Insert the new_node at last node 
            last.next = new_node; 
        } 
   
        // Return the list by head 
    } 
      
    // Method to print the LinkedList. 
    public String toSMPLPair() 
    { 
        Node currNode = this.head;

		String str = "(";
   
        // Traverse through the LinkedList 
        while (currNode != null) { 
            // Print the data at current node 
            str = str + currNode.data.toString() + ", "; 
   
            // Go to next node 
            currNode = currNode.next; 
        }
		str = str.substring(0, str.length()-2); 
		str = str + ")";
          
        return str; 
    }
	public String toSMPLlist() 
    { 
        Node currNode = this.head;

		String str = "[";
   
        // Traverse through the LinkedList 
        while (currNode != null) { 
            // Print the data at current node 
            str = str + currNode.data.toString() + ", "; 
   
            // Go to next node 
            currNode = currNode.next; 
        }
		str = str.substring(0, str.length()-2); 
		str = str + "]";
          
        return str; 
    }
		public SMPLExp findByIndex(int ind) { 
        Node currNode = this.head; 
		int count = 0;
        // Traverse through the LinkedList 
        while (currNode != null) { 
            if (count==ind){
            return currNode.data; 
			}
            // Go to next node 
            currNode = currNode.next; 
			count = count + 1;
        }

			return new SMPLString("Not Found!");
          
    }
		
	public static SMPLExp isPair(SMPLPair exp){
		String str = exp.getVal();
		str = str.substring(1,str.length()-1);
		String[] arrOfStr = str.split(",", 5);
		String temp = "";
		for (String a : arrOfStr) 
            temp = temp + a;
		if(temp.length()>3){
			return new SMPLBoolean(new Boolean(false));
		}else{
			return new SMPLBoolean(new Boolean(true));
		}
	}
	
	public Node reverse(Node node)
    {
        Node prev = null;
        Node current = node;
        Node next = null;
        while (current != null) {
            next = current.next;
            current.next = prev;
            prev = current;
            current = next;
        }
        node = prev;
        return node;
    }
	
	public static SMPLExp createSMPLTuple(LinkedList exp){
		ArrayList<SMPLExp> temp = new ArrayList<SMPLExp>();
		LinkedList.Node currNode = exp.head;
        // Traverse through the LinkedList 
        while (currNode != null) { 
            // Add exp to arraylist
            temp.add(currNode.data); 
            currNode = currNode.next; 
        }
		
		return SMPLTuple.createTuple(temp);
	}
} 