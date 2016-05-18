package ua.in.dris4ecoder.parsers;

/**
 * Created by Alex Korneyko on 17.05.2016.
 */
public class ParseArgs {

    public static int port(String[] args) {
        int resultPort = 0;

        for (String arg : args) {
            if (arg.charAt(0) == 'p' && arg.charAt(1) == ':') {
                try {
                    resultPort = Integer.parseInt(arg.substring(2, arg.length()));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return resultPort;
    }

    public static String dbUrl(String[] args){
        String resultDBUrl = "jdbc:mysql://localhost:3306/mysql?useSSL=false";

        for (String arg: args){
            if(arg.charAt(0) == 'u' && arg.charAt(1) == ':'){
                resultDBUrl = arg.substring(2, arg.length());
            }
        }
        return resultDBUrl;
    }

    public static String dbUserName(String[] args){
        String resultDBUsr = "root";

        for (String arg: args){
            if(arg.charAt(0) == 'n' && arg.charAt(1) == ':'){
                resultDBUsr = arg.substring(2, arg.length());
            }
        }
        return resultDBUsr;
    }

    public static String dbUserPass(String[] args){
        String resultDBPss = "root";

        for (String arg: args){
            if(arg.charAt(0) == 'w' && arg.charAt(1) == ':'){
                resultDBPss = arg.substring(2, arg.length());
            }
        }
        return resultDBPss;
    }
}
