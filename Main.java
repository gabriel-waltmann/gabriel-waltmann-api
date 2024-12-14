import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        APIHandler apiHandler = new APIHandler();
        String response = apiHandler.GET("https://jsonplaceholder.typicode.com/users", 5).body();

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            User[] users = objectMapper.readValue(response, User[].class);

            System.out.println(users[0].name);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
