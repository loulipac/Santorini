package Structure;

public class Pile<E> {
    Object[] elements;
    int nb_elem;

    public Pile() {
        elements = new Object[1];
        nb_elem = 0;
    }

    private void resize() {
        if (nb_elem >= elements.length) {
            int n_size = elements.length * 2;
            Object[] n_array = new Object[n_size];
            System.arraycopy(elements, 0, n_array, 0, elements.length);
            elements = n_array;
        }
    }

    public void insert(E elem) {
        resize();
        elements[nb_elem] = elem;
        nb_elem++;
    }

    public E extract() {
        nb_elem--;
        return (E)elements[nb_elem];
    }

    public boolean isEmpty() {
        return nb_elem != 0;
    }
}
