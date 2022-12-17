package ch.mikeko.adventofcode2022.day11;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.function.Function;

public class Monkey {
    private Queue<Long> items = new ArrayDeque<>();
    private Function<Long, Long> operation;
    private long divisibleByFactor;
    private int divisibleTrueRecipient;
    private int divisibleFalseRecipient;
    private long inspectionCount = 0;

    public long inspect(Long item) {
        inspectionCount++;
        return operation.apply(item);
    }

    public void setOperation(Function<Long, Long> operation) {
        this.operation = operation;
    }

    public long getDivisibleByFactor() {
        return divisibleByFactor;
    }

    public void setDivisibleByFactor(long divisibleByFactor) {
        this.divisibleByFactor = divisibleByFactor;
    }

    public int getDivisibleTrueRecipient() {
        return divisibleTrueRecipient;
    }

    public void setDivisibleTrueRecipient(int divisibleTrueRecipient) {
        this.divisibleTrueRecipient = divisibleTrueRecipient;
    }

    public int getDivisibleFalseRecipient() {
        return divisibleFalseRecipient;
    }

    public void setDivisibleFalseRecipient(int divisibleFalseRecipient) {
        this.divisibleFalseRecipient = divisibleFalseRecipient;
    }

    public void pushItem(long item) {
        items.add(item);
    }

    public Queue<Long> getItems() {
        return items;
    }

    public long getInspectionCount() {
        return inspectionCount;
    }
}
