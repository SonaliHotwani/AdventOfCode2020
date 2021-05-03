package advent.of.code.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Problem1 {
    public static void main(String[] args) throws IOException {
        final List<String> rules = Files.readAllLines(Paths.get("resources/Day7_BagRules"));

        HashMap<String, Bag> uniqueBags = new HashMap<>();

        rules.forEach(rule -> {
            final String[] ruleDescription = rule.split("contain");
            final String bagColor = ruleDescription[0].split("bags")[0].trim();
            final String bagContentInString = ruleDescription[1];
            final Integer quantity = 1;
            List<Bag> bagContent = null;

            if (!bagContentInString.contains("no other bags")) {
                final String[] otherBags = bagContentInString.split(",");
                bagContent = Arrays.stream(otherBags).map(otherBag -> {
                    final String otherBagColor = otherBag.split("[0-9]")[1].split("bag")[0].trim();
                    final Bag bag = new Bag(otherBagColor);
                    uniqueBags.putIfAbsent(otherBagColor, bag);
                    return bag;
                }).collect(Collectors.toList());
            }

            if (uniqueBags.get(bagColor) != null) {
                final Bag bag = uniqueBags.get(bagColor);
                bag.setContent(bagContent);
            } else
                uniqueBags.put(bagColor, new Bag(bagColor, quantity, bagContent));
        });


        final Map<String, Bag> bagsContainingShinyGoldBag = getShiny_gold(new HashMap<>(), uniqueBags, "shiny gold");
        System.out.println(bagsContainingShinyGoldBag.size());
    }

    private static Map<String, Bag> getShiny_gold(Map<String, Bag> shiny_gold, HashMap<String, Bag> uniqueBags, String bagColor) {
        final Map<String, Bag> bags = uniqueBags.entrySet()
                .stream()
                .filter(entry -> entry.getValue().content != null && entry.getValue().content.contains(new Bag(bagColor)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        shiny_gold.putAll(bags);
        bags.forEach((color, bag) -> getShiny_gold(shiny_gold, uniqueBags, color));
        return shiny_gold;
    }
}
