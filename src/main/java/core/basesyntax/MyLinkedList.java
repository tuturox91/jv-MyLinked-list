package core.basesyntax;

import java.util.LinkedList;
import java.util.List;

public class MyLinkedList<T> implements MyLinkedListInterface<T> {

    public  Node<T> firstNode;
    public  Node<T> lastNode;

    int size = 0;

    private static class Node<E> {
        private E value;
        private Node<E> next;
        private Node<E> prev;

        Node(Node<E> prev, Node<E> next, E value) {
            this.value = value;
            this.next = next;
            this.prev = prev;
        }
    }

    @Override
    public void add(T value) {
        Node<T> newNode = new Node<>(lastNode, null, value);
        if(size == 0) {
            firstNode = newNode;
            lastNode = newNode;
        } else {
            lastNode.next = newNode;
            lastNode = newNode;
        }
        size++;
    }

    @Override
    public void add(T value, int index) {
        chekIndexInclusive(index);
        Node<T> newNode = new Node<>(null, null, value);
        if (index == size) {
            if(lastNode == null) {
                firstNode = newNode;
                lastNode = newNode;
            } else {
                lastNode.next = newNode;
                newNode.prev = lastNode;
                lastNode = newNode;
            }
        }
        else {
            beforeNode(value,getNode(index));
        }
        size++;
    }

    private void beforeNode(T value, Node<T> node) {
        Node<T> prev = node.prev;
        Node<T> newNode = new Node<>(prev,node, value);
        if(newNode.prev == null) {
            firstNode = newNode;
        } else {
            prev.next = newNode;
        }
        node.prev = newNode;

    }

    private Node<T> getNode(int index) {
        Node<T> f = firstNode;
        Node<T> l = lastNode;
        if(index == size-1) {
            return l;
        } else if(index == 0) {
            return f;
        }else if(index > size/2) {
            for(int i =size-1; i>index; i--) {
                l = l.prev;
            }
            return l;
        } else {
            for(int i =0; i<index; i++) {
                f = f.next;
            }
            return f;
        }
    }

    private void chekIndexInclusive(int index) {
        if(index<0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + " out of bounds.");
        }
    }


    private void chekIndex(int index) {
        int curSize = size -1 < 0 ? 0 : size-1;
        if(index<0 || index > curSize) {
            throw new IndexOutOfBoundsException("Index: " + index + " out of bounds.");
        }
    }

    @Override
    public void addAll(List<T> list) {
        for (T item : list) {
            add(item);
        }
    }

    @Override
    public T get(int index) {
        chekIndex(index);
        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        chekIndex(index);
        Node<T> neededNode =  getNode(index);
        T oldValue = neededNode.value;
        neededNode.value = value;
        return  oldValue;
    }


    @Override
    public T remove(int index) {
        chekIndex(index);
        Node<T> curNode = getNode(index);
        deleteNode(curNode);
        size--;
        return curNode.value;
    }

    private void deleteNode(Node<T> curNode ) {
        Node<T> nextNode =  curNode.next;
        Node<T> prevNode = curNode.prev;
        if(curNode == firstNode) {
            firstNode = curNode.next;
        } else if(curNode == lastNode) {
            lastNode = curNode.prev;
        }
        if(prevNode != null)
            prevNode.next = nextNode;
        if(nextNode != null)
            nextNode.prev = prevNode;
    }

    @Override
    public boolean remove(T object) {

        for(int i=0; i<=size-1; i++) {
            if(getNode(i).value == null && object == null){
                remove(i);
                return true;
            } else if(getNode(i).value ==null) {
                continue;
            }
            if(getNode(i).value.equals(object)) {
                remove(i);
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0 ? true : false;
    }
}
