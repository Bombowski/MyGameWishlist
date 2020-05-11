package mygamewishlist.model.pojo;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Patryk
 *
 * Class used for paginating, it stores an ArrayList, and
 * implements the Iterable class.
 */
public class Pagination<E> implements Iterable<E> {

	private ArrayList<E> al;
	private int itemsPerPage;
	
	public Pagination(ArrayList<E> al, int productsPerPage) {
		this.al = al;
		this.itemsPerPage = productsPerPage;
	}
	
	public Pagination(int productsPerPage) {
		al = new ArrayList<E>();
		this.itemsPerPage = productsPerPage;
	}
	
	/**
	 * Checks if list contains p object.
	 * 
	 * @param p E
	 * @return boolean
	 */
	public boolean contains(E p) {
		return al.indexOf(p) != -1;
	}
	
	/**
	 * Adds an object to the list
	 * 
	 * @param p E
	 */
	public void add(E p) {
		al.add(p);
	}
	
	/**
	 * If p exists, it gets removes.
	 * 
	 * @param p E
	 */
	public void remove(E p) {
		al.remove(p);
	}
	
	/**
	 * If the list contains an element on index i, it gets removed.
	 * 
	 * @param i int
	 */
	public void remove(int i) {
		if (al.size() > i && i > -1) {
			al.remove(i);
		}		
	}

	/**
	 * If the list contains an object on index i, i returns it, else
	 * it returns a null.
	 * 
	 * @param i int
	 * @return E
	 */
	public E get(int i) {
		return al.size() > i && i > -1 ? (E) al.get(i) : null;
	}
	
	/**
	 * If the list is of size i or bigger, it sets p on index i.
	 */
	public void set(int i, E p) {
		if (al.size() > i && i > -1) {
			al.set(i, p);
		}
	}
	
	/**
	 * Removes all of the elements from the list.
	 */
	public void clear() {
		al.clear();
	}

	/**
	 * Returns the number of elements from the list
	 * 
	 * @return int
	 */
	public int size() {
		return al.size();
	}
	
	/**
	 * If the list contains the object p, it returns it's position
	 * 
	 * @param p E
	 * @return int
	 */
	public int indexOf(E p) {
		return al.indexOf(p);
	}
		
	/**
	 * Returns total number of pages that can be generated with the number
	 * of items that there is in the list, and the value of itemsPerPage.
	 * 
	 * @return int
	 */
	public int getTotalPag() {
		int size = this.size();
		return size % itemsPerPage != 0 ? size / itemsPerPage + 1 : size / itemsPerPage; 
	}
	
	/**
	 * Returns the number of page on which the object p can be found.
	 * 
	 * @param p E
	 * @return int
	 */
	public int getPagByE(E p) {
		return al.indexOf(p) / itemsPerPage;
	}
		
	/**
	 * Returns one page by the provided number, each page is a list of
	 * objects.
	 * 
	 * @param num int
	 * @return ArrayList<E>
	 */
	public ArrayList<E> getPag(int num) {
		if (num < 0) {
			return new ArrayList<E>();
		}
		
		ArrayList<E> toReturn = new ArrayList<E>();
		
		int productoInicial = num * itemsPerPage;
		int productoFinal = productoInicial + itemsPerPage;
				
		for (int i = productoInicial; i < productoFinal; i++) {
			if (al.size() - i >= 1) {
				toReturn.add((E) al.get(i));	
			}
		}
		return toReturn;
	}

	@Override
	public Iterator<E> iterator() {
		return this.al.iterator();
	}
}
