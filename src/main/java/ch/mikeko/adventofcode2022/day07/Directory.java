package ch.mikeko.adventofcode2022.day07;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Directory implements Record {
    private final Directory parentDirectory;
    private final String name;
    private final List<Record> records;

    public Directory(Directory parentDirectory, String name) {
        this.parentDirectory = parentDirectory;
        this.name = name;
        records = new ArrayList<>();
    }

    @SuppressWarnings("UnusedReturnValue")
    public Directory addRecord(Record record) {
        records.add(record);
        return this;
    }

    public Optional<Directory> getParentDirectory() {
        return Optional.ofNullable(parentDirectory);
    }

    @Override
    public String getName() {
        return name;
    }

    public List<Record> getRecords() {
        return records;
    }

    @Override
    public int getSize() {
        return records.stream().mapToInt(Record::getSize).sum();
    }

    public List<Directory> getSubdirectories() {
        return records.stream()
                .filter(x -> x instanceof Directory)
                .map(x -> (Directory) x)
                .collect(Collectors.toList());
    }
}
