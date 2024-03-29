import com.zks888.api.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zks888
 * @date 2018/05/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class PasswordEncryptor {

  @Autowired private PasswordEncoder passwordEncoder;

  @Test
  public void encode() {
    final String admin = this.passwordEncoder.encode("test");
    final String user = this.passwordEncoder.encode("test");
    System.err.println("admin password = " + admin);
    System.err.println("editor password = " + user);
  }
}
