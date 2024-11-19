import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        // יצירת רשימה חדשה של אובייקטים מסוג Purchase
        List<Purchase> purchases = new ArrayList<>();

        // יצירת אובייקטים של User שונים
        User user1 = new User("1", "Alice", "alice@example.com");
        User user2 = new User("2", "Bob", "bob@example.com");
        User user3 = new User("3", "Charlie", "charlie@example.com");

        // הוספת 10 רכישות לרשימה
        for (int i = 0; i < 10; i++) {
            // בחרו באופן אקראי או ברצף איזה משתמש למלא (הדוגמה משתמשת במודולו כדי לחזור על המשתמשים)
            User user = (i % 3 == 0) ? user1 : (i % 3 == 1) ? user2 : user3;
            Date d = new Date(System.currentTimeMillis());
            purchases.add(new Purchase(user, (i + 1) * 50d, new Date(d.getTime() + i*1000)));  // המחיר משתנה וגדל ב-50 כל פעם
        }

        // הדפסת תוצאות
        for (Purchase purchase : purchases) {
            System.out.println("User ID: " + purchase.user().id());
            System.out.println("User Name: " + purchase.user().name());
            System.out.println("Price: " + purchase.price());
            System.out.println("Date: " + purchase.date());
            System.out.println();
        }

        ReportHandler.createReportPerUser(purchases);
    }
}