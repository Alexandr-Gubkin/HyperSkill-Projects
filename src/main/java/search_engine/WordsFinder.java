package search_engine;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

//Strategy
class WordsFinder {

    private final WordsFindStrategy strategy;

    public WordsFinder(WordsFindStrategy strategy) {
        this.strategy = strategy;
    }

    public void find(Scanner sc, Map<String, ArrayList<Integer>> hashMap, String[] data) {
        this.strategy.getResult(sc, hashMap, data);
    }
}
