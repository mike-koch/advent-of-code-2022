package ch.mikeko.adventofcode2022.day16;

import ch.mikeko.adventofcode2022.common.InputParser;
import ch.mikeko.adventofcode2022.common.InputType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PartTwo {
    public static void main(String[] args) {
        try (Stream<String> lines = InputParser.getInputByLine(16, InputType.PUZZLE_INPUT)) {
            var allLines = lines.collect(Collectors.toList());
            var valves = buildValveMap(allLines);
            var floydWarshallMatrix = new FloydWarshall(buildFloydWarshallMatrix(valves)).doFloydWarshall();
            var allPressures = processMatrix("AA", valves, new LinkedHashSet<>(), floydWarshallMatrix, 26, new HashMap<>(), 0);

            var allDisjointPairs = getAllDisjointPairs(allPressures);
            var result = Integer.MIN_VALUE;
            for (var disjointPair : allDisjointPairs) {
                result = Math.max(result, disjointPair.getCombinedPressure());
            }

            System.out.printf("Part two result: %s%n", result);
        }
    }

    private static Map<String, Valve> buildValveMap(List<String> allLines) {
        var valves = new HashMap<String, Valve>();
        var currentMatrixIndex = 0;

        for (var line : allLines) {
            // Afterwards: AA|0|DD,II,BB
            var formattedLine = line.replace("Valve ", "")
                    .replace(" has flow rate=", "|")
                    .replace("valves", "valve")
                    .replace("tunnels", "tunnel")
                    .replace("leads", "lead")
                    .replace("; tunnel lead to valve ", "|")
                    .replaceAll(", ", ",")
                    .split("\\|");
            var neighbors = formattedLine[2].split(",");

            if (!valves.containsKey(formattedLine[0])) {
                valves.put(formattedLine[0], new Valve(formattedLine[0], currentMatrixIndex++));
            }

            valves.get(formattedLine[0]).setFlowRate(Integer.parseInt(formattedLine[1]));

            // Add its neighbors to the map
            for (var neighbor : neighbors) {
                valves.get(formattedLine[0]).addNeighbor(neighbor);
                if (!valves.containsKey(neighbor)) {
                    valves.put(neighbor, new Valve(neighbor, currentMatrixIndex++));
                }
            }
        }

        return valves;
    }

    private static int[][] buildFloydWarshallMatrix(Map<String, Valve> valves) {
        var matrix = new int[valves.keySet().size()][valves.keySet().size()];
        for (int[] ints : matrix) {
            Arrays.fill(ints, FloydWarshall.INF);
        }

        for (var valve : valves.values()) {
            matrix[valve.getMatrixIndex()][valve.getMatrixIndex()] = 0;
            for (var neighbor : valve.getNeighbors()) {
                var neighborValve = valves.get(neighbor);
                matrix[valve.getMatrixIndex()][neighborValve.getMatrixIndex()] = 1;
            }
        }

        return matrix;
    }

    // Don't ask what happened to this method signature.
    private static Map<Set<String>, Integer> processMatrix(String currentValveName,
                                           Map<String, Valve> allValves,
                                           Set<String> openedValves,
                                           int[][] floydWarshallMatrix,
                                           int remainingTime,
                                           Map<Set<String>, Integer> existingResults,
                                           int currentFlowRate) {
        if (remainingTime < 0) {
            //-- Can't make it to this valve
            return existingResults;
        }

        var currentValve = allValves.get(currentValveName);
        var nonZeroValves = getDistancesToUsefulValves(currentValve, allValves, openedValves, floydWarshallMatrix, remainingTime);

        var result = currentFlowRate;
        if (currentValve.getFlowRate() > 0 && !openedValves.contains(currentValveName)) {
            //-- Turn on the valve since we're here
            remainingTime--;
            result += (currentValve.getFlowRate() * remainingTime);
            openedValves.add(currentValveName);

            existingResults.put(openedValves, Math.max(existingResults.getOrDefault(openedValves, -1), result));
        }

        for (var nonZeroValve : nonZeroValves.entrySet()) {
            processMatrix(nonZeroValve.getKey(),
                    allValves,
                    cloneList(openedValves),
                    floydWarshallMatrix,
                    remainingTime - nonZeroValve.getValue(),
                    existingResults,
                    result);
        }



        return existingResults;
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private static Map<String, Integer> getDistancesToUsefulValves(Valve currentValve,
                                                                   Map<String, Valve> valves,
                                                                   Set<String> openValves,
                                                                   int[][] floydWarshall,
                                                                   int remainingTime) {
        Map<String, Integer> validCandidates = new HashMap<>();
        var candidates = floydWarshall[currentValve.getMatrixIndex()];
        for (var i = 0; i < candidates.length; i++) {
            final int finalI = i;
            var candidateDistance = candidates[i];
            var valve = valves.values().stream()
                    .filter(x -> x.getMatrixIndex() == finalI)
                    .findFirst()
                    .get();
            if (candidateDistance > remainingTime ||
                    valve.getFlowRate() == 0 ||
                    currentValve.getName().equals(valve.getName()) ||
                    openValves.contains(valve.getName())) {
                //-- No point going to a valve that we can't open in time
                //-- No point going to a valve with 0 flow rate
                //-- No point going to a valve that's already open
                //-- Can't go to itself
                continue;
            }

            //-- Potentially useful. Add to list.
            validCandidates.put(valve.getName(), candidateDistance);
        }

        return validCandidates;
    }

    @SuppressWarnings("StringOperationCanBeSimplified")
    private static Set<String> cloneList(Set<String> list) {
        Set<String> newList = new LinkedHashSet<>();
        list.forEach(x -> newList.add(new String(x)));

        return newList;
    }

    private static Set<DisjointPair> getAllDisjointPairs(Map<Set<String>, Integer> allValves) {
        Set<DisjointPair> results = new HashSet<>();

        for (var entry : allValves.entrySet()) {
            var otherValves = allValves.entrySet().stream().filter(x -> x != entry).collect(Collectors.toList());
            for (var otherEntry : otherValves) {
                Set<String> entryClone = new HashSet<>(entry.getKey());
                entryClone.retainAll(otherEntry.getKey());

                if (entryClone.isEmpty()) {
                    results.add(new DisjointPair(entry, otherEntry));
                }
            }
        }

        return results;
    }
}
