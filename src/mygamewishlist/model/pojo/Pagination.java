package mygamewishlist.model.pojo;
import java.util.ArrayList;
import java.util.Iterator;

public class Pagination<E> implements Iterable<E> {

	private ArrayList<E> al;
	private int productsPerPage;
	
	public Pagination(ArrayList<E> al, int productsPerPage) {
		this.al = al;
		this.productsPerPage = productsPerPage;
	}
	
	public Pagination(int productsPerPage) {
		al = new ArrayList<E>();
		this.productsPerPage = productsPerPage;
	}
	
	public boolean contains(E p) {
		return al.contains(p);
	}
	
	public void add(E p) {
		al.add(p);
	}
	
	public void remove(E p) {
		al.remove(al.indexOf(p));
	}
	
	public void remove(int i) {
		al.remove(i);
	}

	public E get(int i) {
		return (E) al.get(i);
	}
	
	public void set(int i, E p) {
		al.set(i, p);
	}
	
	public void clear() {
		al.clear();
	}

	public int size() {
		return al.size();
	}
	
	public int indexOf(E p) {
		return al.indexOf(p);
	}
		
	public int getTotalPag() {
		int size = this.size();
		return size % productsPerPage != 0 ? size / productsPerPage + 1 : size / productsPerPage; 
	}
	
	public int getPagByE(E p) {
		return al.indexOf(p) / productsPerPage;
	}
		
	public ArrayList<E> getPag(int num) {
		if (num < 0) {
			return new ArrayList<E>();
		}
		
		ArrayList<E> toReturn = new ArrayList<E>();
		
		int productoInicial = num * productsPerPage;
		int productoFinal = productoInicial + productsPerPage;
				
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
