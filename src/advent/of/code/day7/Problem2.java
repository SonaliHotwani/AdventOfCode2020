package advent.of.code.day7;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Problem2 {
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
                    final int otherBagQuantity = Integer.parseInt(otherBag.replaceAll("[^0-9]", ""));
                    final Bag bag = new Bag(otherBagColor);
                    bag.setQuantity(otherBagQuantity);
                    return bag;
                }).collect(Collectors.toList());
            }
            uniqueBags.put(bagColor, new Bag(bagColor, quantity, bagContent));
        });

        final Bag shiny_gold = uniqueBags.get("shiny gold");
        final Integer totalSum = getBagsSum(uniqueBags, shiny_gold, new AtomicInteger(0), shiny_gold.quantity);

        final ZonedDateTime now = ZonedDateTime.now(ZoneId.of("UTC"));
        System.out.println(totalSum);
        System.out.println(now);
        System.out.println(now.toLocalDate());
    }

    private static Integer getBagsSum(HashMap<String, Bag> uniqueBags, Bag bag, AtomicInteger atomicInteger, Integer quantity) {
        if (bag.content != null) {
            for (Bag bag1 : bag.content) {
                atomicInteger.addAndGet(quantity * bag1.quantity);
                final Bag bagWithQuantity = uniqueBags.get(bag1.color);
                getBagsSum(uniqueBags, bagWithQuantity, atomicInteger, quantity * bag1.quantity);
            }
        }
        return atomicInteger.get();
    }

}
