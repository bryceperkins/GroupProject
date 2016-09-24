package shared.communication;

public class Request {
    private User user;
    public Request() { }
    public Request(String body) { }
    public Response submit() {
        Response tmp = new Response();
        return tmp;
    }
}
