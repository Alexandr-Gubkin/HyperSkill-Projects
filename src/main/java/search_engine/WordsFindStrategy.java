package search_engine;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

interface WordsFindStrategy {

    void getResult(Scanner sc, Map<String, ArrayList<Integer>> hashMap, String[] data);
}
