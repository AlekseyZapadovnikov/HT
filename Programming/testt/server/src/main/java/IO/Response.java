package IO;

import managers.NetworkMessage;

public class Response implements NetworkMessage {
    private boolean isError = false;
    private Exception exception;

    public Response(Exception e){
        isError = true;
        exception = e;
    }
}
