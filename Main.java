import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) throws JsonProcessingException {
        APIHandler apiHandler = new APIHandler();

        try {
            APIHandler.Response<User[]> response = apiHandler
                .GET("https://jsonplaceholder.typicode.com/users");

            System.out.println(response.data[0].name);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
