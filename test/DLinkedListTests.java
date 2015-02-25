import static org.junit.Assert.*;

import org.junit.Test;


public class DLinkedListTests {

	@Test
	public void newListIsEmpty() {
		DLinkedList<?> list = new DLinkedList<>();
		assertTrue(list.isEmpty());
	}
	
	@Test
	public void firstItemInListIsAtPos0() {
		DLinkedList<Integer> list = new DLinkedList<>();
		list.add(0);
		assertTrue(list.get(0) == 0);
	}
	
	@Test
	public void addingTwoItemsToListStayInOrder() {
		DLinkedList<Integer> list = new DLinkedList<>();
		list.add(0);
		list.add(1);
		assertTrue(list.get(0) == 0);
		assertTrue(list.get(1) == 1);
		assertTrue(list.size() == 2);
	}
	
	@Test
	public void addingItemInMiddleOfListIsInMiddle() {
		DLinkedList<Integer> list = new DLinkedList<>();
		list.add(0);
		list.add(2);
		list.add(1, 1);
		assertTrue(list.get(1) == 1);
		assertTrue(list.size() == 3);
	}
	
	@Test
	public void addItemsAtStart() {
		DLinkedList<Integer> list = new DLinkedList<>();
		list.add(1);
		list.add(2);
		list.add(0, 0);
		assertTrue(list.get(0) == 0);
		assertTrue(list.get(1) == 1);
		assertTrue(list.size() == 3);
	}

	@Test
	public void removeItemAtStartMovesStart() {
		DLinkedList<Integer> list = new DLinkedList<>();
		list.add(0);
		list.add(1);
		assertTrue(list.get(0) == 0);
		assertTrue(list.remove(0) == 0);
		assertTrue(list.get(0) == 1);
	}
	
	@Test
	public void removeItemAtTailMovesTail() {
		DLinkedList<Integer> list = new DLinkedList<>();
		list.add(0);
		list.add(1);
		assertTrue(list.remove(1) == 1);
		list.add(2);
		assertTrue(list.get(1) == 2);
	}
	
	@Test
	public void removeItemInMiddleRemovesNodeFromChain() {
		DLinkedList<Integer> list = new DLinkedList<>();
		list.add(0);
		list.add(5);
		list.add(2);
		assertTrue(list.remove(1) == 5);
		assertTrue(list.get(1) == 2);
	}
}
