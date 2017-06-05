/**
 * D-Heap
 */

//TODO: check cases where there are a few items with the same key

public class DHeap {

	private int size, max_size, d;
	/**
	 * An array holding the elements of this heap
	 */
	private DHeap_Item[] array;

	// Constructor
	// m_d >= 2, m_size > 0
	DHeap(int m_d, int m_size) {
		max_size = m_size;
		d = m_d;
		array = new DHeap_Item[max_size];
		size = 0;
	}

	/**
	 * public int getSize()
	 * Returns the number of elements in the heap.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * public int arrayToHeap()
	 * <p>
	 * The function builds a new heap from the given array.
	 * Previous data of the heap should be erased.
	 * preconidtion: array1.length() <= max_size
	 * postcondition: isHeap()
	 * size = array.length()
	 * Returns number of comparisons along the function run.
	 */
	public int arrayToHeap(DHeap_Item[] array1) {

		array = array1;
		size = array1.length;
		int comparisonsMade = 0;

		for (int i = 0; i < array1.length; i++) { // Setting pos
			array1[i].setPos(i);
		}

		for (int i = getSize() / 2; i > -1; i--) {
			comparisonsMade += heapifyDown(i);
		}

		return comparisonsMade;

		// TODO: what do they mean by number of comparisons? each heapifyDown in each heapifyDown call
		// TODO: or d*(each heapifyDown in each heapifyDown) [for each child]
	}

	/**
	 * public void heapifyDown(int)
	 * Compares the replacement element with the min of children,
	 * if the min child is smaller than the element in the index elemIndex, we move the child up, we continue
	 * doing this until heap order is correct and put the replacement element in correct index.
	 * Complexity in w.c is O(h) where h is the height of the item defined using elemIndex, because we travel all the way to a leaf.
	 * NOTE: that for heapifyDown(root) the complexity is O(log_d(n)), where n is the number of items in this heap
	 *
	 * @param elemIndex the index of the element to preform the heapify down to.
	 * @return The number of comparisons made
	 */
	public int heapifyDown(int elemIndex) {

		DHeap_Item[] children = childrenNodes(elemIndex);
		int smallestChildIndex = findSmallestChild(elemIndex, children);
		if (smallestChildIndex != elemIndex) {
			switchItems(smallestChildIndex, elemIndex);
			return children.length + heapifyDown(smallestChildIndex);
		}

		return children.length;
	}

	/**
	 * Finds and returns the index of the smallest child of the parent defined by the index elemIndex.
	 * Complexity is O(d), because we compare all the children of the given parent.
	 *
	 * @param elemIndex the index of the parent to get his largest child
	 * @param children  The children of the item at the index elemIndex
	 */
	public int findSmallestChild(int elemIndex, DHeap_Item[] children) {

		DHeap_Item minItem = array[elemIndex];

		for (int i = 0; i < children.length; i++) {
			if (children[i] != null && children[i].getKey() < minItem.getKey()) {
				minItem = children[i];
			}
		}
		return minItem.getPos();
	}

	/**
	 * Get the children of an item.
	 * Complexity is O(d), adding each children to the array.
	 *
	 * @param elemIndex he index of the parent to get his children
	 * @return an Array with all the children of the given parent.
	 */
	private DHeap_Item[] childrenNodes(int elemIndex) {
		DHeap_Item[] temp = new DHeap_Item[d];
		for (int i = 0; i < d; i++) {
			int childIndex = child(elemIndex, i, d);
			if (childIndex < getSize()) {
				temp[i] = array[child(elemIndex, i, d)];
			}
		}
		return temp;
	}

	/**
	 * Switches the positions of array[item1Index] and array[item2Index]
	 */
	private void switchItems(int item1Index, int item2Index) {

		DHeap_Item temp = array[item1Index];
		array[item1Index] = array[item2Index];
		array[item1Index].setPos(item1Index);
		array[item2Index] = temp;
		array[item2Index].setPos(item2Index);
	}

	/**
	 * public boolean isHeap()
	 * <p>
	 * The function returns true if and only if the D-ary tree rooted at array[0]
	 * satisfies the heap property or has size == 0.
	 */
	public boolean isHeap() {

		if (getSize() == 0) return true;

		// TODO: Implement. Make sure we are doing this in the best complexity possible.
		return false;
	}


	/**
	 * public static int parent(i,d), child(i,k,d)
	 * (2 methods)
	 * <p>
	 * precondition: i >= 0, d >= 2, 1 <= k <= d
	 * <p>
	 * The methods compute the index of the parent and the k-th child of
	 * vertex i in a complete D-ary tree stored in an array.
	 * Note that indices of arrays in Java start from 0.
	 */
	public static int parent(int i, int d) {
		// TODO: check this.
		return (i - 1) / d;
	}

	public static int child(int i, int k, int d) {
		return (d * i) + 1 + k;
	}

	/**
	 * public int Insert(DHeap_Item item)
	 * <p>
	 * Inserts the given item to the heap.
	 * Returns number of comparisons during the insertion.
	 * <p>
	 * precondition: item != null
	 * isHeap()
	 * size < max_size
	 * <p>
	 * postcondition: isHeap()
	 */
	public int Insert(DHeap_Item item) {
		item.setPos(size);
		array[size++] = item;

		return heapifyUp(getSize() - 1);
	}

	/**
	 * Heapify the item at position itemIndex up.
	 *
	 * @return The number of comparisons during the heapify.
	 */
	private int heapifyUp(int itemIndex) {
		DHeap_Item tmp = array[itemIndex];
		int comparisonsMade = 0;
		while (itemIndex > 0 && tmp.getKey() < array[parent(itemIndex, d)].getKey()) {

			switchItems(itemIndex, parent(itemIndex, d));
			itemIndex = parent(itemIndex, d);
			comparisonsMade++;

		}
		switchItems(itemIndex, tmp.getPos());
		return comparisonsMade;
	}

	/**
	 * public int Delete_Min()
	 * <p>
	 * Deletes the minimum item in the heap.
	 * Returns the number of comparisons made during the deletion.
	 * <p>
	 * precondition: size > 0
	 * isHeap()
	 * <p>
	 * postcondition: isHeap()
	 */
	public int Delete_Min() {
		return Delete(Get_Min());
	}


	/**
	 * public DHeap_Item Get_Min()
	 * <p>
	 * Returns the minimum item in the heap.
	 * <p>
	 * precondition: heapsize > 0
	 * isHeap()
	 * size > 0
	 * <p>
	 * postcondition: isHeap()
	 */
	public DHeap_Item Get_Min() {
		return array[0];
	}

	/**
	 * public int Decrease_Key(DHeap_Item item, int delta)
	 * <p>
	 * Decerases the key of the given item by delta.
	 * Returns number of comparisons made as a result of the decrease.
	 * <p>
	 * precondition: item.pos < size;
	 * item != null
	 * isHeap()
	 * <p>
	 * postcondition: isHeap()
	 */
	public int Decrease_Key(DHeap_Item item, int delta) {
		// TODO: Implement.
		return 0;
	}

	/**
	 * public int Delete(DHeap_Item item)
	 * <p>
	 * Deletes the given item from the heap.
	 * Returns number of comparisons during the deletion.
	 * <p>
	 * precondition: item.pos < size;
	 * item != null
	 * isHeap()
	 * <p>
	 * postcondition: isHeap()
	 */
	public int Delete(DHeap_Item item) {

		int itemPos = item.getPos();

		switchItems(itemPos, getSize() - 1);

		array[getSize()-1] = null;
		size--;

		if (getSize() == 0) {
			return 0;
		}

		return heapifyDown(itemPos);
	}

	/**
	 * Sort the input array using heap-sort (build a heap, and
	 * perform n times: get-min, del-min).
	 * Sorting should be done using the DHeap, name of the items is irrelevant.
	 * <p>
	 * Returns the number of comparisons performed.
	 * <p>
	 * postcondition: array1 is sorted
	 */
	public static int DHeapSort(int[] array1, int d) {

		DHeap heap = new DHeap(d, array1.length);
		DHeap_Item[] items = new DHeap_Item[array1.length];
		int comparisonsMade = 0;

		for (int i = 0; i < array1.length; i++) {
			items[i] = new DHeap_Item(" " + array1[i], array1[i]);
			items[i].setPos(i);
		}
		comparisonsMade += heap.arrayToHeap(items); // Build the heap.

		// Get the sorted elements:
		for (int i = 0; heap.getSize() != 0; i++) {
			array1[i] = heap.Get_Min().getKey();
			comparisonsMade += heap.Delete_Min();
		}
		return comparisonsMade;

	}

	public void printHeap() {
		for (int i = 0; i < getSize(); i++) {
			System.out.print(array[i].getKey() + ", ");
		}
		System.out.print("\n");
	}
}
