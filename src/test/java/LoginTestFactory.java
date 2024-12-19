import org.testng.annotations.Factory;

public class LoginTestFactory {

    @Factory
    public Object[] createInstances() {
        return new Object[]{
                new LoginTest("firefox"),
                new LoginTest("edge")
        };
    }
}