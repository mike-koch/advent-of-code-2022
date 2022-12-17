package ch.mikeko.adventofcode2022.day13;

import java.util.ArrayList;
import java.util.List;

public class Packet {
    private String textRepresentation = "";
    private final List<Object> entities;

    public Packet(Object... items) {
        entities = new ArrayList<>(List.of(items));
    }

    public void setTextRepresentation(String textRepresentation) {
        this.textRepresentation = textRepresentation;
    }

    @Override
    public String toString() {
        return "Packet{" + textRepresentation + "}";
    }

    public int size() {
        return entities.size();
    }

    public Object get(int index) {
        return entities.get(index);
    }

    public void add(Object object) {
        entities.add(object);
    }
}
