package junit.test;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.BeforeClass;
import org.junit.Test;

import com.foshanshop.ejb3.QSender;

public class QSenderTest {
	private static QSender sender;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		 Properties props = new Properties();
         props.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
         props.setProperty(Context.PROVIDER_URL, "localhost:1099");
         props.setProperty(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
         InitialContext ctx = new InitialContext(props);
         sender = (QSender) ctx.lookup("QSenderBean/remote");
	}
	
	@Test
	public void testSend() {
		sender.send();
	}
}
