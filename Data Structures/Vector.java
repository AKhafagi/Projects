
/**
 * Vector Class 
 * designs a generic dynamic Vector class that expands and contracts when needed.
 * monday Febuary 22nd, 2016
 * Computer Science 310
 * San Diego State University
 * @author Anas Khafagi
 */


import java.util.AbstractList;
import java.util.Collection;

public class Vector<E> extends AbstractList<E> {
	protected E[] elementData;
	protected int capacity;
	protected int elementCount;
	protected int capacityIncerement;
	protected final static int DEFAULT_CAPACITY = 10;

	/**
	 * Constructs an empty vector with the specified initial capacity and with
	 * its capacity increment equal to zero.
	 * 
	 * @param intialCapacity
	 *            the intial size for the vector.
	 * @throws IllegalArgumentException
	 *             - if the specified initial capacity is negative
	 */
	public Vector(int intialCapacity) {
		this(intialCapacity, 0);
	}

	/**
	 * Constructs an empty vector so that its internal data array has size 10
	 * and its standard capacity increment is zero.
	 */
	public Vector() {
		this(DEFAULT_CAPACITY, 0);
	}

	/**
	 * Constructs a vector containing the elements of the specified collection,
	 * in the order they are returned by the collection's iterator.
	 * 
	 * @param c
	 *            the collection whose elements are to be placed into this
	 *            vector
	 * @throws NullPointerException
	 *             - if the specified collection is null
	 */
	public Vector(Collection<? extends E> c) {
		this();
		if (!c.equals(null)) {
			throw new NullPointerException(" The Specified collection is null ");
		}
		addAll(c);

	}

	/**
	 * Constructs a Vector with size IntialCapacity and capacityIncrement
	 * blockSize.
	 * 
	 * @param intialCapacity
	 *            the initial capacity of the vector.
	 * @param blockSize
	 *            the amount by which the capacity is increased when the vector
	 *            overflows.
	 * @throws IllegalArgumentException
	 *             - if the specified initial capacity is negative
	 */
	@SuppressWarnings("unchecked")
	public Vector(int intialCapacity, int blockSize) {
		super();
		if (intialCapacity < 0)
			throw new IllegalArgumentException("Invalid Size please, try again!!");
		this.elementData = (E[]) new Object[intialCapacity];
		this.capacityIncerement = blockSize;
		this.capacity = intialCapacity;
	}

	public boolean add(E element) {
		if (element.equals(null)) {
			throw new NullPointerException(
					"The specified element is null and this Vector does not permit null elements");
		}
		if (isNearCapacity()) {
			this.expand();
		}
		this.elementData[elementCount++] = element;
		modCount++;
		return true;

	}

	public E get(int index) {
		if (index >= elementCount || index < 0) {
			throw new IndexOutOfBoundsException("the index is out of range");
		}
		return this.elementData[index];
	}

	public E set(int index, E element) {
		E temp = null;
		if (element.equals(null)) {
			throw new NullPointerException(
					"The specified element is null and this Vector does not permit null elements");
		}
		if (index < 0 || index >= this.size()) {
			throw new IndexOutOfBoundsException("the index is out of range");
		}
		temp = this.elementData[index];
		this.elementData[index] = element;
		return temp;
	}

	public void add(int index, E element) {
		if (element.equals(null)) {
			throw new NullPointerException(
					"The specified element is null and this Vector does not permit null elements");
		}
		if (index < 0 || index > this.size()) {
			throw new IndexOutOfBoundsException("the index is out of range");
		}
		if (isNearCapacity()) {
			this.expand();
		}
		this.shift("shiftRight", index);
		this.elementData[index] = element;
		this.elementCount++;
	}

	public E remove(int index) {
		E temp;
		if (index >= this.size()) {
			throw new IndexOutOfBoundsException("the index is out of range");
		}
		temp = elementData[index];
		elementData[index] = null;
		this.shift("shiftLeft", index);
		this.elementCount--;
		modCount++;
		this.contract();
		return temp;
	}

	public boolean isEmpty() {
		return elementCount == 0;
	}

	/**
	 * returns the capacity of the vector
	 * 
	 * @return the capaicty of the vector.
	 */
	public int capacity() {
		return elementData.length;
	}

	public boolean contains(Object element) {
		if (element.equals(null)) {
			throw new NullPointerException(
					"The specified element is null and this Vector does not permit null elements");
		}
		for (int i = 0; i < size(); i++) {
			if (this.elementData[i].equals(element)) {
				return true;
			}

		}
		return false;
	}

	public void clear() {
		for (int i = 0; i < elementCount; i++) {
			this.elementData[i] = null;
		}
		this.elementCount = 0;
		modCount++;

	}

	/**
	 * Contracts the capacity of the Vector to the number of elements in the
	 * Vector
	 */
	protected void contract() {
		this.elementData = copyOf(elementData, elementCount);
	}

	public int indexOf(Object element) {
		if (element.equals(null)) {
			throw new NullPointerException(
					"The specified element is null and this Vector does not permit null elements");
		}
		for (int i = 0; i < size(); i++) {
			if (elementData[i].equals(element)) {
				return i;
			}

		}
		return -1;
	}

	public int size() {
		return this.elementCount;
	}

	/**
	 * Expands the capacity of the Vector to either double the intial size, or
	 * padded with <code>capacityIncerement</code>
	 */
	protected void expand() {
		if (capacityIncerement == 0) {
			elementData = (E[]) copyOf(elementData, elementData.length * 2);
		} else {
			elementData = copyOf(elementData, (elementData.length + capacityIncerement));
		}

	}

	/**
	 * shifts all the elements of the Vector 1 spot to the right or left.
	 * 
	 * @param argument
	 *            wether to shift right or left.
	 * @param index
	 *            the index where the shift should start from.
	 */
	protected void shift(String argument, int index) {
		if (argument.equals("shiftRight")) {
			for (int i = elementCount; i >= index; i--) {

				if (i == index) {
					this.elementData[i] = null;
					return;
				}
				this.elementData[i] = this.elementData[i - 1];
			}
		}
		if (argument.equals("shiftLeft")) {
			for (int j = index; j < elementCount; j++) {
				if (j == elementCount - 1) {
					this.elementData[j] = null;
					return;
				}
				this.elementData[j] = this.elementData[j + 1];
			}
		}
	}

	/**
	 * Copies the elements of the array into a copy array of size
	 * <code> newLength </code>
	 * 
	 * @param elementData2
	 *            the array to copy from.
	 * @param newlength
	 *            the size of the new array.
	 * @return the copy array with all the elements from the old array.
	 */
	@SuppressWarnings("unchecked")
	protected E[] copyOf(E[] elementData2, int newlength) {
		int count = 0;
		E[] elementDataCopy = (E[]) new Object[newlength];
		for (Object element : elementData2) {
			if (count >= newlength) {
				return elementDataCopy;
			}
			elementDataCopy[count++] = (E) element;
		}
		return elementDataCopy;
	}

	/**
	 * checks wether the array is near the capacity
	 * 
	 * @return true - if the array is near the capacity false- otherwise
	 */
	protected boolean isNearCapacity() {
		return (this.elementData.length - 1 == this.elementCount);

	}
}
