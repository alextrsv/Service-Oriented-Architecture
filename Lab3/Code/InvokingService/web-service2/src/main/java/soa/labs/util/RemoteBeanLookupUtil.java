package soa.labs.util;

import lombok.SneakyThrows;
import org.wildfly.naming.client.WildFlyInitialContextFactory;
import soa.labs.services.WorkerService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

public class RemoteBeanLookupUtil {

    @SneakyThrows
    public static WorkerService lookupBarsBean() {
        return (WorkerService) getContext().lookup("java:global/ejb/WorkerServiceImpl");
    }

    private static Context getContext() throws NamingException {
        Properties props = new Properties();
        props.put(Context.INITIAL_CONTEXT_FACTORY, WildFlyInitialContextFactory.class.getName());
        return new InitialContext(props);
    }


}