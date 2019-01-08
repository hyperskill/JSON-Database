package org.hyperskill.database.serverside;

public class Storage {
    private static final int SIZE = 1000;
    private String[] storage = new String[SIZE];

    private boolean indexInRange(int index) {
        return index > 0 && index <= SIZE;
    }

    void set(int index, String str) {
        if (indexInRange(index)) {
            storage[index - 1] = str;
        } else {
            throw new IllegalArgumentException("Size is " + SIZE + ", got index " + index);
        }
    }

    String get(int index) {
        String result;
        if (indexInRange(index)) {
            result = storage[index - 1];
            if (!result.equals("")) {
                return result;
            }
        } else {
            throw new IllegalArgumentException("Size is " + SIZE + ", got index " + index);
        }
        return null;
    }

    void delete(int index) {
        if (indexInRange(index)) {
            storage[index - 1] = "";
        } else {
            throw new IllegalArgumentException("Size is " + SIZE + ", got index " + index);
        }
    }
}
