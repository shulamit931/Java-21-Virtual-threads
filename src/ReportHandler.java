import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;


public class ReportHandler {


    static GsonBuilder gb = new GsonBuilder().setPrettyPrinting();
    static Gson gson = gb.create();
    public static Runnable createReport(List<Purchase> purchases, String ts) {
        return new Runnable() {
            @Override
            public void run() {
                String jsonString = gson.toJson(new UserWithPurchases(purchases.getFirst().user(), purchases
                        .stream()
                        .collect(Collectors.toMap(Purchase::date, Purchase::price))));

                try {
                    Path dp = Paths.get(("./files/" + ts).replace(':', '_'));
                    if (!Files.exists(dp)) {
                        Files.createDirectories(dp);

                    }
                    Files.write(Paths.get(("./files/" + ts + "/" + purchases.get(0).user().id() + ".json").replace(':', '_')), jsonString.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

                } catch (IOException e) {
                    System.out.println("ERROR" + e);
                }


            }

        };

    }

    public static void createReportPerUser(List<Purchase> purchases) {

//        List<Boolean> collect = purchases.stream()
//                .collect(Collectors
//                        .collectingAndThen(Collectors.groupingBy(p -> p.user().id()),
//                                map -> map.values().parallelStream()
//                                        .map(this::createReport)
//                                        .map(r -> Thread.ofVirtual().start(r))
//                                        .map(t -> {
//                                            try {
//                                                t.join();
//                                                return true;
//                                            } catch (Exception e) {
//                                                return false;
//                                            }
//                                        })
//                                        .collect(Collectors.toList())));

        String ts = new Date().toString();
        purchases.stream().collect(Collectors.groupingBy(p -> p.user().id()))
                .values()
                .parallelStream()
                .map(p -> createReport(p, ts))
                .map(r -> Thread.ofVirtual().start(r))
                .forEach(t -> {
                    try {
                        t.join();

                    } catch (Exception e) {
                        System.out.println("ERROR" + e);
                    }
                });
    }
}
