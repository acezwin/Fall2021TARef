package DataStructures;

import ADTs.ListADT;
import Exceptions.ElementNotFoundException;
import Exceptions.EmptyCollectionException;
import Exceptions.InvalidArgumentException;
/**
 * 
 * @author ITSC 2214 Q.
 * @param <T> 
 * @version 1.0
 */
public class CircularDoublyLinkedList<T extends Comparable<T>> implements ListADT<T> {
    private DoublyLinkedNode<T> first;
    private int numNodes;

    /**
     * Constructor.
     */
    public CircularDoublyLinkedList() {
        first = null;
        numNodes = 0;
    }
    
    
    /**
     * Retrieve the first element in the circular list,
     * which is stored in the current first node
     * @return first element
     */
    @Override
    public T first() throws EmptyCollectionException{
        if (first == null)
            throw new EmptyCollectionException("CircularDoublyLinkedList");

        return first.getElement();
    }
    
    /**
     * retrieve the element of the current first node in the list
     * @return 
     */
    public T current(){
        if (first == null)
            return null;

        return first.getElement();
    }
    
    /**
     * Retrieve the last element in the list,
     * which next reference points to the current first node
     * @return
     * @throws EmptyCollectionException 
     */
    @Override
    public T last() throws EmptyCollectionException{
        if (first == null)
            throw new EmptyCollectionException("CircularDoublyLinkedList");

        return first.getPrev().getElement();
    }

    @Override
    /**
     * Examine whether the circular list is empty.
    */
    public boolean isEmpty() {
        return (first == null);
    }

    /**
     * Print all elements in the list.
     */
    public void printList() {
        // Is list empty?
        if(first == null)
            return;

        // start at the head and print everyone
        boolean startFlag = true;
        for(DoublyLinkedNode tmp=first; (tmp != first) || startFlag;
                      tmp=tmp.getNext()) {
            System.out.println(tmp.getElement().toString());
            startFlag = false;
        }
    }
    
    /**
     * Add a new element in the end of the circular list,
     * which next reference points to the current first node.
     * @param element 
     */
    @Override
    public void addLast(T element) {
        //TODO create the new node
        DoublyLinkedNode<T> addedNode = new DoublyLinkedNode();	  	
        addedNode.setElement(element);  	
        //TODO adjust references for new node  	
        if (numNodes == 0) {	  	
            addedNode.setNext(addedNode);  	
            addedNode.setPrev(addedNode);  	
            first = addedNode;	
        }
        //change all corresponding previous and next pointers
        else {  	
            first.getPrev().setNext(addedNode);  	
            addedNode.setNext(first); 	
            addedNode.setPrev(first.getPrev()); 	
            first.setPrev(addedNode);	
        }
 	
        //TODO increase the numNodes variable
        numNodes++;
    }
    /**
     * Add a new element in the beginning of the circular list,
     * which is represented as the current first node.
     */
    @Override
    public void addFirst(T element) {
        // create the new node
        DoublyLinkedNode<T> addNode = new DoublyLinkedNode();
        addNode.setElement(element);
        if (numNodes == 0) {
            addNode.setNext(addNode);
            addNode.setPrev(addNode);
            first = addNode;
        }
        //TODO adjust the current first node reference
        else {
            addNode.setPrev(first.getPrev());
            first.getPrev().setNext(addNode);
            first.setPrev(addNode);
            addNode.setNext(first);
            first = addNode;
        }
        numNodes++;
        
        //or
        //addLast(element);
        //first = first.getPrev();
    }
    
    /**
     * Insert a new node which holds the reference to the given element,
     * after the node which holds the given existing.
     * Namely insert the given element after the existing element in the list
        If the given existing is not found, throw an 
        ElementNotFoundException.

        Note: the CircularDoublyLinkedList class uses the data member variable first
        and numNodes to maintain the status of a CircularDoublyLinkedList instance.
        When the method addAfter is invoked, we need to make sure that the
        data member variable first, and numNodes are changed properly.
        Do we need to change the data member variable first?
        And when?
     * @param existing
     * @param element
     * @throws ElementNotFoundException
     * @throws EmptyCollectionException 
     */
    @Override
    public void addAfter(T existing, T element) throws ElementNotFoundException, EmptyCollectionException {
        DoublyLinkedNode<T> foundNode = find(existing);
        DoublyLinkedNode<T> addNode = new DoublyLinkedNode();
        addNode.setElement(element);
        if (numNodes == 0) {
            throw new EmptyCollectionException("The list is empty");
        }
        else if (foundNode == null){
            throw new ElementNotFoundException("The element was not found");
        }
        //TODO create a node and maintain all references to 
        //insert the new node after the found node.
        else if (foundNode == first) {
            addNode.setPrev(first);
            addNode.setNext(first.getNext());
            first.getNext().setPrev(addNode);
            first.setNext(addNode);
        }
        else {
            addNode.setNext(foundNode.getNext());
            foundNode.getNext().setPrev(addNode);
            foundNode.setNext(addNode);
            addNode.setPrev(foundNode);
        }	  	
        numNodes++;	  	
    }
    /**
     * Remove a node that holds the given element.
     * @param element
     * @return
     * @throws EmptyCollectionException
     * @throws ElementNotFoundException 
     */
    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        DoublyLinkedNode<T> foundNode = find(element);
        T temp = foundNode.getElement();
        if (numNodes == 0) {
            throw new EmptyCollectionException("The list is empty");
        }
        else if (foundNode == null) {
            throw new ElementNotFoundException("The element was not found");
        }
        else if (numNodes == 1) {
            first.setNext(null);
            first.setPrev(null);
            first = null;
        }
        else if (foundNode == first) {
            first.getNext().setPrev(first.getPrev());
            first.getPrev().setNext(first.getNext());
            first = first.getNext();
        }
 
        else {
        //TODO remove the found node and maintain all
        //references, including the current first node reference
        foundNode.getNext().setPrev(foundNode.getPrev());
        foundNode.getPrev().setNext(foundNode.getNext());
        foundNode.setNext(null);
        foundNode.setPrev(null);
        }
        numNodes--;
        return temp;
//        //orrrrrr
//        DoublyLinkedNode<T> foundNode = find(element);	  	
//        // case 1: empty list	
//        if(first == null)	
//            throw new EmptyCollectionException("CircularDoublyLinkedList");
//        if (this.numNodes == 1){ 
//            //case 2
//            first.setNext(null);
//            first.setPrev(null);
//            first = null;
//        } else {
//            //case 3
//            foundNode.getPrev().setNext(foundNode.getNext());
//            foundNode.getNext().setPrev(foundNode.getPrev());	  	
//        }	  	
//        numNodes--; 	
//        // return a pointer to the removed node
//        return foundNode.getElement();
    }

    /**
     * Remove the first node in the list.
     * (prior to the current first node)
     * @return
     * @throws EmptyCollectionException 
     */
    @Override
    public T removeFirst() throws EmptyCollectionException {
        // case 1: empty list
        if(first == null)
            throw new EmptyCollectionException("CircularDoublyLinkedList");
        
        DoublyLinkedNode<T> tmp = first;
        if (this.numNodes == 1){ 
            //case 2
            first.setNext(null);
            first.setPrev(null);
            first = null;
        } else {
            //case 3
            first.getPrev().setNext(first.getNext());
            first.getNext().setPrev(first.getPrev());
            first = tmp.getNext();
        }
        numNodes--;
        // return a pointer to the removed node
        return tmp.getElement();
    }

    /**
     * Remove the last node in the list.
     * (prior to the current first node)
     * @return
     * @throws EmptyCollectionException 
     */
    @Override
    public T removeLast() throws EmptyCollectionException {
        // TODO remove last node and maintain all references
        //      including the current first node reference.
        //case 1: empty list
        if (first == null) {
            throw new EmptyCollectionException("The list is empty");
        }
        DoublyLinkedNode<T> tmp = first.getPrev();
        if (numNodes == 1) {
               first.setNext(null);
               first.setPrev(null);
               first = null;
        }
        else {
            first.getPrev().getPrev().setNext(first);
            first.setPrev(first.getPrev().getPrev());
            tmp.setNext(null);
            tmp.setPrev(null);
        }
        numNodes--;
        // return a pointer to the removed node
        return tmp.getElement();     
    }

    /**
     * Examine whether the list includes the given element.
     * @param element
     * @return
     * @throws EmptyCollectionException 
     */
    @Override
    public boolean contains(T element) throws EmptyCollectionException {
        //TODO refer to the printList method
        boolean startFlag = true;
        boolean result = false;
        if (first == null) {
            throw new EmptyCollectionException("The list is empty");
        }
        for (DoublyLinkedNode tmp = first; (tmp != first) || startFlag; tmp = tmp.getNext()) {
            if (tmp.getElement() == element) {
                result = true;
                return result;
            }
            startFlag = false;
        }
        return result;
    }
    
    /**
     * Find the first node which holds the given element.
     * @param element
     * @return
     * @throws EmptyCollectionException 
     */
    public DoublyLinkedNode<T> find(T element) throws ElementNotFoundException, EmptyCollectionException {
        if (numNodes == 0)
            throw new EmptyCollectionException("CircularDoublyLinkedList");

        // walk through the list and find the given element in the list
        boolean startFlag = true;
        for(DoublyLinkedNode<T> tmp=first; (tmp != first) || startFlag;
                      tmp=tmp.getNext()) {
            startFlag = false;
            if (element.compareTo(tmp.getElement()) == 0)
                return tmp;
        }
        throw new ElementNotFoundException("CircularDoublyLinkedList"); 
    }
    
    /**
     * Retrieve the node at the given index of the list.
     * @param index
     * @return
     * @throws EmptyCollectionException
     * @throws InvalidArgumentException 
     */
    public DoublyLinkedNode<T> getNode(int index) throws EmptyCollectionException, InvalidArgumentException {
        if (numNodes == 0)
            throw new EmptyCollectionException("CircularDoublyLinkedList");

        if (index < 0 || index >= numNodes) 
            throw new InvalidArgumentException("CircularDoublyLinkedList");
        
        // TODO walk through the list till the given index
        DoublyLinkedNode<T> tmp=first;
        for (int count = 0; count < index; count++)
            tmp=tmp.getNext();

        return tmp;
    }
    
    /**
     * Change the current first node reference to its next node.
     */
    public void next() {
        if(this.first != null)
            first = first.getNext();
    }
    
    /**
     * Retrieve the element at the given index of the list.
     * @param index
     * @return
     * @throws EmptyCollectionException
     * @throws InvalidArgumentException 
     */
    @Override
    public T get(int index) throws EmptyCollectionException, InvalidArgumentException {
        DoublyLinkedNode<T> tmp = getNode(index);
        return tmp.getElement();
    }

    /**
     * Reset the element in the given index of the list.
     * @param index
     * @param element
     * @throws EmptyCollectionException
     * @throws InvalidArgumentException 
     */
    @Override
    public void set(int index, T element) throws EmptyCollectionException, InvalidArgumentException {
        DoublyLinkedNode<T> tmp = getNode(index);
        tmp.setElement(element);
    }

    /**
     * Return the total number of elements in the list.
     * @return 
     */
    @Override
    public int size() {
        return this.numNodes;
    }
}
