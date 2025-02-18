package org.transformice.libraries;

public record Pair<K, V>(K first, V second) {
    @Override
    public String toString() {
        return "(" + this.first + ", " + this.second + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) obj;
        return this.first.equals(pair.first) && this.second.equals(pair.second);
    }

    public K getFirst() {
        return this.first;
    }

    public V getSecond() {
        return this.second;
    }
}