package lynn.util.timewheel;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Test {

    public static void main(String[] args) {
        HashedWheelTimer timer = new HashedWheelTimer(10, TimeUnit.SECONDS, 10);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        timer.newTimeout((timeout) -> {
            System.out.println(sdf.format(new Date()) + ": 1111");
        }, 1, TimeUnit.MINUTES);
        timer.newTimeout((timeout) -> {
            System.out.println(sdf.format(new Date()) + ": 2222");
        }, 2, TimeUnit.MINUTES);

    }



}
