/**
 * See ListADT for comments 
 */
public class DLinkedList<E> implements ListADT<E> {
	/**
	 * 1st node in linked list
	 */
	private Listnode<E> head;
	/**
	 * Tail reference node
	 */
	private Listnode<E> tail;
	/**
	 * Number of items in list
	 */
	private int numItems;
	
	/**
	 * Construct doubly-linked list
	 */
	public DLinkedList(){
		// Initialize node references and numItems
		head = null;
		tail = null;
		numItems = 0;
	}

	/**
	 * {@inheritDoc}
	 */
	public void add(E item) {
		// Prevent null items from being inserted
		if(item == null) throw new IllegalArgumentException("item");
		// Create new node
		Listnode<E> node = new Listnode<E>(item, null, tail);
		// Inserting at end, so tail will always be prev, even if tail == null
		if(head == null) {
			head = tail = node; // If first node, set as both head & tail to the new node
		}
		else {
			//Otherwise, set node as next from tail and advance tail to node
			tail.setNext(node);
			tail = node;
		}
		++numItems; // Always increase numItems if item added
	}


	/**
	 * {@inheritDoc}
	 */
	public void add(int pos, E item) {
		// Throw exceptions if item == null or the position is invalid
		if(item == null) throw new IllegalArgumentException("item");
		if(pos < 0 || pos > numItems) throw new IndexOutOfBoundsException("pos");
		if(pos == numItems) { // If inserting at end, use add(E item)
			add(item);
			return;
		}
		Listnode<E> node = new Listnode<E>(item); // Create new node
		if(pos == 0) { // If at front
			// Make node a new head
			node.setNext(head);
			head.setPrev(node);
			head = node;
		}
		else {
			// Otherwise, insert at location in the list given by pos
			Listnode<E> curr = head;
			for(int i = 0; i < pos - 1; ++i) {
				curr = curr.getNext();
			}
			node.setNext(curr.getNext());
			node.setPrev(curr);
			curr.setNext(node);
			node.getNext().setPrev(node);
		}
		++numItems; // Increment numItems if inserted
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean contains(E item) {
		// Throw exception if item == null
		if(item == null) throw new IllegalArgumentException("item");
		Listnode<E> curr = head;
		while(curr != null) { // Loop through nodes
			if(curr.getData().equals(item)) // Until node holds data that == item
				return true; // item found
		}
		return false; // item not found
	}


	/**
	 * {@inheritDoc}
	 */
	public E get(int pos) {
		// Throw exception if pos is out of range
		if(pos < 0 || pos >= numItems) throw new IndexOutOfBoundsException("pos");
		Listnode<E> curr = head;
		for(int i = 0; i < pos; ++i) { // Loop through pos nodes
			curr = curr.getNext();
		}
		return curr.getData(); // Return node data
	}


	/**
	 * {@inheritDoc}
	 */
	public boolean isEmpty() {
		return size() == 0;
	}


	/**
	 * {@inheritDoc}
	 */
	public E remove(int pos) {
		// Throw exception if pos is out of range
		if(pos < 0 || pos >= numItems) throw new IndexOutOfBoundsException("pos");
		E data;
		if(pos == 0) { // If removing head
			data = head.getData(); // get data and advance head
			head = head.getNext();
		}
		else if(pos == numItems - 1) { // If removing tail
			data = tail.getData(); // Move tail to prev
			tail = tail.getPrev();
		}
		else { // Otherwise loop till pos
			Listnode<E> curr = head;
			for(int i = 0; i < pos; ++ i) {
				curr = curr.getNext();
			}
			// and exclude node from chain
			Listnode<E> prev = curr.getPrev();
			Listnode<E> next = curr.getNext();
			prev.setNext(next);
			next.setPrev(prev);
			data = curr.getData(); // and get data
		}
		--numItems; // Decrement numItems
		return data; // Return data
	}


	/**
	 * {@inheritDoc}
	 */
	public int size() {
		return numItems;
	}
	
}