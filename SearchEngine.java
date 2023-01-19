import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class ServerHandler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    ArrayList<String> strings = new ArrayList<String>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return "Strings: " + strings.toString();
        } 
        else {
            System.out.println("Path: " + url.getPath());
            if (url.getPath().contains("/search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    strings.add(parameters[1]);
                    //return String.format("String added! There are now %s strings stored!", strings.size());
                    ArrayList<String> stringsToPrint = new ArrayList<>();
                    for (int i = 0; i < strings.size(); i++) {
                        if (strings.get(i).contains(parameters[1])) {
                            stringsToPrint.add(strings.get(i));
                        }
                    }

                    return "Strings Found: " + stringsToPrint.toString();
                }
            }
            else if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    strings.add(parameters[1]);
                    return String.format("String added! There are now %s strings stored!", strings.size());
                }
            }
            return "404 Not Found!";
        }
    }
}

class SearchEngine {
    public static void main(String[] args) throws IOException {
        if(args.length == 0){
            System.out.println("Missing port number! Try any number between 1024 to 49151");
            return;
        }

        int port = Integer.parseInt(args[0]);

        Server.start(port, new ServerHandler());
    }
}
