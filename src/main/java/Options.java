import java.util.Arrays;

public class Options {

    private final ConsolePrinter consolePrinter;
    private final InputCollector inputCollector;
    private final Service service;

    public Options(ConsolePrinter consolePrinter, InputCollector inputCollector, Service service) {
        this.consolePrinter = consolePrinter;
        this.inputCollector = inputCollector;
        this.service = service;
    }

    public void mainLoop() {
        boolean running = true;
        do {
            consolePrinter.print("Menu główne" ,Arrays.stream(getMainOptions()).toList());
            System.out.print("Wybierz: ");
            int userInput = inputCollector.getInput(getMainOptions().length);
            Options.OptionsMenu option = Options.OptionsMenu.values()[userInput];
            switch (option) {
                case EXIT -> {
                    running = false;
                    System.out.println("Zamykam kontakt z bazą");
                }
                case LOAD -> {
                    consolePrinter.print("Dostępne tabele" ,service.getAllTableNames());
                }
            }
        } while (running);
    }

    String[] getMainOptions() {
        String[] options = new String[OptionsMenu.values().length];
        for (int i = 1; i < OptionsMenu.values().length; i++) {
            OptionsMenu option = OptionsMenu.values()[i];
            options[i - 1] = option.ordinal() + " " + option.getDesc();
        }

        options[options.length - 1] = OptionsMenu.values()[0].ordinal() + " " + OptionsMenu.values()[0].getDesc();
        return options;
    }


    enum OptionsMenu {
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
