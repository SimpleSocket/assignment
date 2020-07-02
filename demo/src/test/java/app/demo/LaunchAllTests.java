package app.demo;

import app.demo.controllers.AuthenticationTests;
import app.demo.controllers.BlogControllerSecurityTests;
import app.demo.controllers.BlogControllerTests;
import com.peper.blog.controllers.Authentication;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AuthenticationTests.class,
        BlogControllerSecurityTests.class,
        BlogControllerTests.class,
})
public class LaunchAllTests {
}
