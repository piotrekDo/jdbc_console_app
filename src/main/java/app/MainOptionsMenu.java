package app;

import java.util.Arrays;

public class MainOptionsMenu {

    private final ConsolePrinter consolePrinter;
    private final InputCollector inputCollector;
    private final Service service;

    public MainOptionsMenu(ConsolePrinter consolePrinter, InputCollector inputCollector, Service service) {
        this.consolePrinter = consolePrinter;
        this.inputCollector = inputCollector;
        this.service = service;
    }

    /**
     * Main loop of the program. Displaying possible entry options such as retrieving tables.
     */

    public void mainLoop() {
        boolean running = true;
        do {
            consolePrinter.print("Menu główne", Arrays.stream(getMainOptions()).toList());
            System.out.print("Wybierz: ");
            int userInput = inputCollector.getNumericInput(getMainOptions().length);
            OptionsMenu option = OptionsMenu.values()[userInput];
            switch (option) {
                case EXIT -> {
                    running = false;
                    System.out.println("Zamykam kontakt z bazą");
                }
                case LOAD -> {
                    new DisplayAvalibleTables(consolePrinter, inputCollector, service).loadOptions();
                }
            }
        } while (running);
    }

    /**
     * Auxiliary method used to extract description fields from OptionsMenu enum.
     * Returns array of string required by ConsolePrinter to display possible options.
     */

    private String[] getMainOptions() {
        String[] options = new String[OptionsMenu.values().length];
        for (int i = 1; i < OptionsMenu.values().length; i++) {
            OptionsMenu option = OptionsMenu.values()[i];
            options[i - 1] = option.ordinal() + " " + option.getDesc();
        }

        options[options.length - 1] = OptionsMenu.values()[0].ordinal() + " " + OptionsMenu.values()[0].getDesc();
        return options;
    }


    private enum OptionsMenu {
        EXIT("Zakończ program"),
        LOAD("Wczytaj dane");

        private final String desc;

        public String getDesc() {
            return desc;
        }

        OptionsMenu(String desc) {
            this.desc = desc;
        }
    }
}
