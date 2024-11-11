package it.unibo.mvc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.controller.DrawNumberControllerImpl;
import it.unibo.mvc.model.DrawNumberImpl;

/**
 * Application entry-point.
 */
public final class LaunchApp {

    private final static int VIEWS_NUMBER = 3;
    private final static String SWING_VIEW_CLASSNAME = "it.unibo.mvc.view.DrawNumberStdoutView";
    private final static String STDOUT_VIEW_CLASSNAME = "it.unibo.mvc.view.DrawNumberSwingView";

    private LaunchApp() { }

    /**
     * Runs the application.
     *
     * @param args ignored
     * @throws ClassNotFoundException if the fetches class does not exist
     * @throws NoSuchMethodException if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException if the constructor throws exceptions
     * @throws IllegalAccessException in case of reflection issues
     * @throws IllegalArgumentException in case of reflection issues
     */
    public static void main(final String... args) {
        final var model = new DrawNumberImpl();
        final DrawNumberController app = new DrawNumberControllerImpl(model);
        // Load the view classes via reflection and get all the constructors
        final Collection<Class<?>> viewClasses = new ArrayList<>();
        for (String className: new String[]{SWING_VIEW_CLASSNAME, STDOUT_VIEW_CLASSNAME}) {
            try {
                viewClasses.add(Class.forName(className)); 
            } catch (ClassNotFoundException e) {
                System.err.println(e);
            }
        }
        final Collection<Constructor<?>[]> constructors = new ArrayList<>();
        for (Class<?> c: viewClasses) {
            constructors.add(c.getConstructors());
        }
        // For every array of constructors, find and launch the 0-ary constructor
        for (Constructor<?>[] constructorsArray: constructors) {
            for (Constructor<?> c: constructorsArray) {
                if (c.getParameterCount() == 0) {
                    try {
                        for (int i = 0; i < VIEWS_NUMBER; i++) {
                            app.addView((DrawNumberView)c.newInstance());
                        }
                    } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
                        System.err.println(e);
                    }
                } 
            }
        }
    }
}
