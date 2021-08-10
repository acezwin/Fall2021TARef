package Shopping;

import DataStructures.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @version Fall 2019
 * @author ITCS 2214
 */
public class ShoppingListArrayList implements ShoppingListADT {

    private ArrayList<Grocery> shoppingList;

    /**
     * Default constructor of ShoppingArray object.
     */
    public ShoppingListArrayList() {
        this.shoppingList = new ArrayList<>();
    }

    /**
     * Constructor of ShoppingArray object that parses from a file.
     *
     * @param filename the name of the file to parse
     * @throws FileNotFoundException if an error occurs when parsing the file
     */
    public ShoppingListArrayList(String filename) throws FileNotFoundException {
        this.shoppingList = new ArrayList<>();
        scanFile(filename);
    }

    /**
     * Method to add a new entry. Only new entries can be added. Combines 
     * quantities if entry already exists.
     *
     * @param entry the entry to be added
     */
    @Override
    public void add(Grocery entry) {
        if (entry == null) {
            return;
        }
        
        // Check if this item already exists
        if (this.contains(entry)) {
            //Merge the quantity of new entry into existing entry
            combineQuantity(entry);
            return;
        }

        shoppingList.add(entry);
    }

    /**
     * Method to remove an entry.
     *
     * @param ent to be removed
     * @return true when entry was removed
     * @throws DataStructures.ElementNotFoundException
     */
    @Override
    public boolean remove(Grocery ent) {
        
        // the boolean found describes whether or not we find the
        // entry to remove
        
        boolean found = false;

        // TODO Search the parameter variable ent in the shoppingList; if it is found, please remove it and set the value of `found`. It is okay to directly use methods from the Java Build-in ArrayList class.
        for (int i = 0; i < shoppingList.size(); i++) {  	
             if (shoppingList.get(i).equals(ent)){	
                 shoppingList.remove(i);
                 found = true;
 	  	
            }
 	  	
        }

        
        // Return false if not found        
        return found;
    }

    /**
     * Method to find an entry.
     *
     * @param index to find
     * @return the entry if found
     * @throws Exceptions.EmptyCollectionException
     */
    @Override
    public Grocery find(int index) throws IndexOutOfBoundsException,
            EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("ECE - find");
        }
        
        // TODO check whether or not the given index is legal
        // for example, the given index is less than 0 or falls outside of the size. 
        // If it is not legal, throw an IndexOutOfBoundsException  	
        if (index < 0 || index > shoppingList.size()) {  	
            throw new IndexOutOfBoundsException("Index not legal, less than "
                + "0 or larger than array size");	  	
        }



        // return the corresponding entry in the shoppingList
        // need to change the return value
        // return null;
        return (Grocery)this.shoppingList.get(index);
    }

    /**
     * Method to locate the index of an entry.
     *
     * @param ent to find the index
     * @return the index of the entry
     * @throws ElementNotFoundException if no entry was found
     */
    @Override
    public int indexOf(Grocery ent) throws ElementNotFoundException {
        if (ent != null) {
            for (int i = 0; i < shoppingList.size(); i++) {
                if (shoppingList.get(i).compareTo(ent) == 0) {
                    return i;
                }
            }
        }
        throw new ElementNotFoundException("indexOf");
    }

    /**
     * Method to determine whether the object contains an entry.
     *
     * @param ent to find
     * @return true if and only if the entry is found
     */
    @Override
    public boolean contains(Grocery ent) {
        boolean hasItem = false;
       
        if (ent != null) {
            // given item, named ent, in the list. If found, return true. 	
            // Either using methods from Java build-in ArrayList method or   	
            // writing your own loop is fine.	
            for (int i = 0; i < shoppingList.size(); i++) {
                if (shoppingList.get(i).compareTo(ent) == 0) {
                    hasItem = true;
                }
            }
        }
        return hasItem;
    }

    /**
     * Gets the size of the collection.
     *
     * @return the size of the collection
     */
    @Override
    public int size() {
        return shoppingList.size();
    }

    /**
     * Gets whether the collection is empty.
     *
     * @return true if and only if the collection is empty
     */
    @Override
    public boolean isEmpty() {
        return shoppingList.isEmpty();
    }

    /**
     * Returns a string representing this object.
     *
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(String.format("%-25s", "NAME"));
        s.append(String.format("%-18s", "CATEGORY"));
        s.append(String.format("%-10s", "AISLE"));
        s.append(String.format("%-10s", "QUANTITY"));
        s.append(String.format("%-10s", "PRICE"));
        s.append('\n');
        s.append("------------------------------------------------------------"
                + "-------------");
        s.append('\n');
        for (int i = 0; i < shoppingList.size(); i++) {
            s.append(String.format("%-25s", shoppingList.get(i).getName()));
            s.append(String.format("%-18s", shoppingList.get(i).getCategory()));
            s.append(String.format("%-10s", shoppingList.get(i).getAisle()));
            s.append(String.format("%-10s", shoppingList.get(i).getQuantity()));
            s.append(String.format("%-10s", shoppingList.get(i).getPrice()));
            s.append('\n');
            s.append("--------------------------------------------------------"
                    + "-----------------");
            s.append('\n');
        }

        return s.toString();
    }

    /**
     * Add the quantity of a duplicate entry into the existing
     *
     * @param entry duplicate
     */
    private void combineQuantity(Grocery entry) {
        try {
            int index = this.indexOf(entry);
            shoppingList.get(index).setQuantity(
                    shoppingList.get(index).getQuantity()
                    + entry.getQuantity());
        } 
        catch (ElementNotFoundException e) {
            System.out.println("combineQuantity - ECE");
        }

    }

    /**
     * Scans the specified file to add items to the collection.
     *
     * @param filename the name of the file to scan
     * @throws FileNotFoundException if the file is not found
     */
    private void scanFile(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(getClass().getResourceAsStream(filename))
                .useDelimiter("(,|\r\n)");

        while (scanner.hasNext()) {
            Grocery temp = new Grocery(scanner.next(), scanner.next(),
                    Integer.parseInt(scanner.next()),
                    Float.parseFloat(scanner.next()),
                    Integer.parseInt(scanner.next()));
            
            add(temp);
        }
    }

}
