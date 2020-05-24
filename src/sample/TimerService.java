package sample;

import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

public class TimerService extends ScheduledService<Integer> {
    @Override

    protected Task<Integer> createTask() {

        return new Task<>() {
            @Override
            protected Integer call() throws Exception {
                return 0;
            }
        };

    }

}