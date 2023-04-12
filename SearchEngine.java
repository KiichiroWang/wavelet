import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

class SearchHandler implements URLHandler {
    // The one bit of state on the server: a number that will be manipulated by
    // various requests.
    String word = "null";
    ArrayList<String> list = new ArrayList<>();

    public String handleRequest(URI url) {
        if (url.getPath().equals("/")) {
            return list.toString();
        } else {
            System.out.println("Path: " + url.getPath());

            // Add Query
            if (url.getPath().contains("/add")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    word = parameters[1];
                    list.add(word);
                    return String.format("List has added %s!", parameters[1]);
                }
            }

            // Search
            else if (url.getPath().contains("search")) {
                String[] parameters = url.getQuery().split("=");
                if (parameters[0].equals("s")) {
                    word = parameters[1];
                    String containedWords = "";
                    for (String s : list) {
                        if (s.contains(word)) {
                            containedWords += s +", ";
                        }
                    }

                    return containedWords.substring(0, containedWords.length() - 2);
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

        Server.start(port, new SearchHandler());
    }
}
