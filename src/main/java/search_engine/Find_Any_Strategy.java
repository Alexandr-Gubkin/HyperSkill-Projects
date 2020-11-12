package search_engine;

import java.util.*;

class Find_Any_Strategy implements WordsFindStrategy {

    @Override
    public void getResult(Scanner sc, Map<String, ArrayList<Integer>> hashMap, String[] data) {
        System.out.println("Enter a name or email to search all suitable people.");
        String[] words = sc.nextLine().trim().split("\\s+");
        List<String> list = new LinkedList<>();
        for (String oneWord : words) {
            for (var values : hashMap.entrySet()) {
                if (values.getKey().equalsIgnoreCase(oneWord)) {
                    for (Integer val2 : values.getValue()) {
                        if (!list.contains(data[val2])) {
                            list.add(data[val2]);
                        }
                    }
                }
            }
        }
        if (list.size() == 0) {
            System.out.println("No matching people found.");
        } else {
            System.out.println(list.size() + " persons found:");
            for (String str : list) {
                System.out.println(str);
            }
        }
    }
}
