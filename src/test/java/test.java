import com.librarySys.pojo.User;
import org.junit.Test;

import javax.servlet.http.HttpSession;

public class test {
    @Test
    public String test(){

        User user = new User();
        user.setUuid("asdf1234");
        user.setUgender("男");
        user.setUname("哈哈");
        HttpSession session = null;
        session.setAttribute("user",user);

        System.out.println(session);
        return "";
    }
}
