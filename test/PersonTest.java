import models.Person;
import org.junit.Test;
import play.test.UnitTest;

public class PersonTest extends UnitTest {

    @Test
    public void testFullName() {
        Person person = new Person();
        person.firstName = "John";
        person.lastName = "Doe";

        assertEquals("John Doe", person.getFullName());
    }

}
