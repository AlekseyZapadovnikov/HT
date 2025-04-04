package managers;

import IO.Response;

import java.util.Arrays;

public class ControlManager extends Runner {
    private final String[] needCollection = {"show", "print_ascending"};

    public Response giveResponse(String commandName) {
        boolean isPresent = Arrays.stream(needCollection)
                .anyMatch(element -> element.equals(commandName));
        if (isPresent) {
            return new Response(commandName, colectionManager.getroutes());
        }
        return new Response(commandName);
    }

}
