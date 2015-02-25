/**
 * See ListADT for comments 
 */
public class DLinkedList<E> implements ListADT<E> {
	
	private Listnode<E> head;
	private Listnode<E> tail;
	private int numItems;
	
	public DLinkedList(){
		head = null;
		tail = null;
		numItems = 0;
	}


	public void add(E item) {
		if(item == null) throw new IllegalArgumentException("item");
		Listnode<E> node = new Listnode<E>(item, null, tail);
		if(head == null) {
			head = tail = node;
		}
		else {
			tail.setNext(node);
			node.setPrev(tail);
			tail = node;
		}
		++numItems;
	}


	public void add(int pos, E item) {
		if(item == null) throw new IllegalArgumentException("item");
		if(pos < 0 || pos > numItems) throw new IndexOutOfBoundsException("pos");
		if(pos == numItems) {
			add(item);
			return;
		}
		Listnode<E> node = new Listnode<E>(item);
		if(head == null) {
			head = tail = node;
		}
		else if(pos == 0) {
			node.setNext(head);
			head.setPrev(node);
			head = node;
		}
		else {
			Listnode<E> curr = head;
			for(int i = 0; i < pos - 1; ++i) {
				curr = curr.getNext();
			}
			node.setNext(curr.getNext());
			node.setPrev(curr);
			curr.setNext(node);
		}
		++numItems;
	}


	public boolean contains(E item) {
		if(item == null) throw new IllegalArgumentException("item");
		Listnode<E> curr = head;
		while(curr != null) {
			if(curr.getData().equals(item))
				return true;
		}
		return false;
	}


	public E get(int pos) {
		if(pos < 0 || pos >= numItems) throw new IndexOutOfBoundsException("pos");
		Listnode<E> curr = head;
		for(int i = 0; i < pos; ++i) {
			curr = curr.getNext();
		}
		return curr.getData();
	}


	public boolean isEmpty() {
		return size() == 0;
	}


	public E remove(int pos) {
		if(pos < 0 || pos >= numItems) throw new IndexOutOfBoundsException("pos");
		E data;
		if(pos == 0) {
			data = head.getData();
			head = head.getNext();
		}
		else if(pos == numItems - 1) {
			data = tail.getData();
			tail = tail.getPrev();
		}
		else {
			Listnode<E> curr = head;
			for(int i = 0; i < pos; ++ i) {
				curr = curr.getNext();
			}
			Listnode<E> prev = curr.getPrev();
			Listnode<E> next = curr.getNext();
			prev.setNext(next);
			next.setPrev(prev);
			data = curr.getData();
		}
		--numItems;
		return data;
	}


	public int size() {
		return numItems;
	}
	
	
	

}