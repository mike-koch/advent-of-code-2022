package ch.mikeko.adventofcode2022.day10;

import java.util.HashMap;
import java.util.Map;

public class Cpu {
    private int cycleCount = 1;
    private int xValue = 1;
    private final Map<Integer, Integer> signalStrengths = new HashMap<>();

    public void noop() {
        increaseCycleCount();
    }

    public void addx(int value) {
        increaseCycleCount();
        xValue += value;
        increaseCycleCount();
    }

    private void increaseCycleCount() {
        cycleCount++;

        if (cycleCount == 20 || (cycleCount - 20) % 40 == 0) {
            signalStrengths.put(cycleCount, cycleCount * xValue);
        }
    }

    public Map<Integer, Integer> getSignalStrengths() {
        return signalStrengths;
    }
}
