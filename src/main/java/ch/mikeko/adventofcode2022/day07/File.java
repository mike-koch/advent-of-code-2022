package ch.mikeko.adventofcode2022.day07;

public class File implements Record {
    private final Directory directory;
    private final String name;
    private final int size;

    public File(Directory directory, String name, int size) {
        this.directory = directory;
        this.name = name;
        this.size = size;
    }

    public Directory getDirectory() {
        return directory;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getSize() {
        return size;
    }
}
