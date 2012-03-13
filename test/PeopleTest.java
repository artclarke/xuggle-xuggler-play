import models.Person;
import org.junit.Test;
import play.mvc.Http;
import play.mvc.Http.Response;
import play.test.FunctionalTest;

import java.util.HashMap;

public class PeopleTest extends FunctionalTest {

    @Test
    public void testListPeople() {
        Response response = GET("/people");
        assertIsOk(response);
    }
    
    @Test
    public void testCreatePerson() {
        Response response = POST("/people", new HashMap<String, String>(){{
            put("firstName", "John");
            put("lastName",  "Doe");
        }});

        assertStatus(Http.StatusCode.FOUND, response);
        assertHeaderEquals("Location", "/people", response);
    }

    @Test
    public void testDeletePerson() {
        Person person = new Person();
        person.firstName = "John";
        person.lastName = "Doe";
        person.create();

        Response response = DELETE("/people/" + person.getId());

        assertStatus(Http.StatusCode.OK, response);
    }
}