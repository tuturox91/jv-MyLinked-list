package core.basesyntax;

import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {
    private Node<T> firstNode = null;
    private Node<T> lastNode = null;
    private int size;

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(value, lastNode, null);
        if (size == 0) {
            firstNode = newNode;
            lastNode = newNode;
        } else {
            lastNode.nextNode = newNode;
            lastNode = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + " out of bounds");
        }
        if (size == 0) {
            add(value);
            return;
        } else if (index == 0) {
            Node<T> newNode = new Node<>(value, null, firstNode);
            firstNode.prevNode = newNode;
            firstNode = newNode;
        } else if (index == size) {
            add(value);
            return;
        } else {
            Node<T> oldNode = findNode(index);
            Node<T> newNode = new Node<>(value, null, oldNode);
            oldNode.prevNode.nextNode = newNode;
            oldNode.prevNode = newNode;
        }
        size++;
    }

    @Override
    public void addAll(List<T> list) {
        for (T value : list) {
            add(value);
        }
    }

    @Override
    public T get(int index) {
        return findNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        T oldValue = findNode(index).value;
        findNode(index).value = value;
        return oldValue;
    }

    @Override
    public T remove(int index) {
        Node<T> currentNode;
        currentNode = findNode(index);
        T removedItem = currentNode.value;
        unlinkNode(currentNode);
        size--;
        return removedItem;
    }

    @Override
    public boolean remove(T object) {
        boolean isItemRemoved = false;
        Node<T> currentNode;
        for (int i = 0; i < size; i++) {
            currentNode = findNode(i);
            if (currentNode.value == object
                    || currentNode.value != null && currentNode.value.equals(object)) {
                remove(i);
                isItemRemoved = true;
                break;
            }
        }
        return isItemRemoved;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private class Node<T> {
        private T value;
        private Node<T> nextNode;
        private Node<T> prevNode;

        private Node(T value, Node<T> prevNode, Node<T> nextNode) {
            this.value = value;
            this.prevNode = prevNode;
            this.nextNode = nextNode;
        }
    }

    private Node<T> findNode(int index) {
        if (index < 0 || index >= size) {
            throw new ArrayIndexOutOfBoundsException("Invalid index");
        }
        Node<T> currentNode;
        if (index > (size / 2)) {
            currentNode = lastNode;
            int desiredIndex = size - 1;
            while (index != desiredIndex) {
                currentNode = currentNode.prevNode;
                desiredIndex--;
            }
        } else {
            currentNode = firstNode;
            int desiredIndex = 0;
            while (index != desiredIndex) {
                currentNode = currentNode.nextNode;
                desiredIndex++;
            }
        }
        return currentNode;
    }

    private void unlinkNode(Node<T> node) {
        if (node == firstNode) {
            firstNode = firstNode.nextNode;
        } else if (node == lastNode) {
            lastNode = lastNode.prevNode;
        } else {
            node.prevNode.nextNode = node.nextNode;
            node.nextNode.prevNode = node.prevNode;
        }
    }

}
