package it.unibo.mvc.view;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;

public class DrawNumberStdoutView implements DrawNumberView {
    public static final String START_LABEL = "Starting new game...";

    @Override
    public void setController(DrawNumberController observer) {
        // Beacuse the View is output-only we don't need to send data to the controller
    }

    @Override
    public void start() {
        System.out.println(START_LABEL);
    }

    @Override
    public void result(DrawResult res) {
        System.out.println(res.getDescription());
        if (res.equals(DrawResult.YOU_WON) || res.equals(DrawResult.YOU_LOST)) {
            System.out.println(START_LABEL);
        }
    }

}
