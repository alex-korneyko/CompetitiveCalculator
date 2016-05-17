package ua.in.dris4ecoder.parsers;

/**
 * Created by Alex Korneyko on 17.05.2016.
 */
public class ParseArgs {

    public static int port(String[] args) {
        int resultPort = 0;

        for (String arg : args) {
            if (arg.charAt(0) == 'p') {
                try {
                    resultPort = Integer.parseInt(arg.substring(2, arg.length() - 1));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        return resultPort;
    }
}
