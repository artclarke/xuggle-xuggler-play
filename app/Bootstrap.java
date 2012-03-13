import models.Person;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.test.Fixtures;

@OnApplicationStart
public class Bootstrap extends Job {
    public void doJob() {
        // Check if the database is empty
        if(Person.count() == 0) {
            Fixtures.load("data.yml");
        }
    }
}
