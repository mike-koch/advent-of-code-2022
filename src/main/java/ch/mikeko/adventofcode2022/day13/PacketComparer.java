package ch.mikeko.adventofcode2022.day13;

import java.util.List;

public class PacketComparer {
    public static Status pairIsInOrder(Packet firstList, Packet secondList) {
        for (var i = 0; i < Math.max(firstList.size(), secondList.size()); i++) {
            var first = safeGet(firstList, i);
            var second = safeGet(secondList, i);

            // Left side out of items = ordered, right side out of items = unordered
            if (first == null) {
                return Status.ORDERED;
            }
            if (second == null) {
                return Status.UNORDERED;
            }

            if (first instanceof Integer && second instanceof Integer) {
                if ((int)first < (int)second) {
                    return Status.ORDERED;
                }
                if ((int)first > (int)second) {
                    return Status.UNORDERED;
                }
                //-- Equal? keep going
            } else if ((first instanceof Integer && second instanceof Packet) || (first instanceof Packet && second instanceof Integer)) {
                //-- Mixed types. Convert the int to a list and compare lists
                var actualFirst = first instanceof Integer ? new Packet(first) : (Packet) first;
                var actualSecond = second instanceof Integer ? new Packet(second) : (Packet) second;

                var innerResult = pairIsInOrder(actualFirst, actualSecond);
                if (innerResult != Status.UNKNOWN) {
                    return innerResult;
                }
            } else if (first instanceof Packet && second instanceof Packet) {
                //-- Both lists. Check list contents
                var innerResult = pairIsInOrder((Packet) first, (Packet) second);
                if (innerResult != Status.UNKNOWN) {
                    return innerResult;
                }
            }
        }

        // If two lists are identical, we don't know yet
        return Status.UNKNOWN;
    }

    private static Object safeGet(Packet list, int index) {
        try {
            return list.get(index);
        } catch (IndexOutOfBoundsException ignored) {
            return null;
        }
    }
}
