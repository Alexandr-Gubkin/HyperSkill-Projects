package search_engine;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Find_NONE_Strategy implements WordsFindStrategy {
    @Override
    public void getResult(Scanner sc, Map<String, ArrayList<Integer>> hashMap, String[] data) {
        System.out.println("Enter a name or email to search all suitable people.");
        String[] words = sc.nextLine().trim().split("\\s+");
        List<String> list = new LinkedList<>();
        for (String datum : data) {
            boolean result = true;
            for (String word : words) {
                String pattern = "\\b" + word.toLowerCase() + "\\b";
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(datum.toLowerCase());
                if (m.find()) {
                    result = false;
                    break;
                }
            }
            if (result) {
                list.add(datum);
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
