package RabbitMQ.TestUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Service {

    public int randomNumberGenerator(int minimum, int maximum) {
        Random random = new Random();
        return random.nextInt(maximum - minimum) + minimum;
    }

    public String timeInMillis(int amount, String unit) {

        int multiplier = 0;

        switch (unit.toUpperCase()) {
            case "S":
                multiplier = 1000;
                break;
            case "M":
                multiplier = 60000;
                break;
            case "H":
                multiplier = 3600000;
                break;
        }

        int multipliedValue = amount * multiplier;
        return String.valueOf(multipliedValue);
    }

    public String timestamp(String text) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmssyyyyMMdd");
        String timestamp = simpleDateFormat.format(date);
        System.out.println("Generated timestamp: " + timestamp);
        return text + timestamp;
    }

    public String timestampWithMillis(String text) {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmmss.SSSyyyyMMdd");
        String timestamp = simpleDateFormat.format(date);
        System.out.println("Generated timestamp: " + timestamp);
        return text + timestamp;
    }

    public void testCaseName(String testCaseName) {
        System.out.println("Starting test case: '\u001B[1m\u001B[34m" + testCaseName + "\u001B[0m'");
    }


}
