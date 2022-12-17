package ch.mikeko.adventofcode2022.day10;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Cpu {
    private int cycleCount = 0;
    private int spritePosition = 1;
    private int crtPosition = 0;
    private final Map<Integer, Integer> signalStrengths = new HashMap<>();

    public void noop() {
        increaseCycleCount();
    }

    public void addx(int value) {
        increaseCycleCount();
        increaseCycleCount();
        spritePosition += value;
    }

    private void increaseCycleCount() {
        cycleCount++;

        if (cycleCount == 20 || (cycleCount - 20) % 40 == 0) {
            signalStrengths.put(cycleCount, cycleCount * spritePosition);
        }

        drawCrt();
    }

    public Map<Integer, Integer> getSignalStrengths() {
        return signalStrengths;
    }

    private void drawCrt() {
        var validPixels = Arrays.asList(spritePosition - 1, spritePosition, spritePosition + 1);

        if (validPixels.contains(crtPosition)) {
            System.out.print("#");
        } else {
            System.out.print(".");
        }
        crtPosition++;


        if (cycleCount % 40 == 0) {
            System.out.println();
            crtPosition = 0;
        }
    }
}
