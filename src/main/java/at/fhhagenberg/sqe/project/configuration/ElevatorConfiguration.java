package at.fhhagenberg.sqe.project.configuration;

import at.fhhagenberg.sqe.project.connection.IElevatorAdapterFactory;
import at.fhhagenberg.sqe.project.connection.rmi.RMIElevatorFactory;
import at.fhhagenberg.sqe.project.services.automatic.IAutomaticModeServiceFactory;
import at.fhhagenberg.sqe.project.services.automatic.advanced.AdvancedAutomaticModeServiceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by rknoll on 06/01/15.
 */
@Configuration
@ComponentScan(value = {"at.fhhagenberg.sqe.project.services.model"})
public class ElevatorConfiguration {

    @Bean
    public IElevatorAdapterFactory getIElevatorAdapterFactory() {
        return new RMIElevatorFactory();
    }

    @Bean
    public IAutomaticModeServiceFactory getIAutomaticModeServiceFactory() {
        return new AdvancedAutomaticModeServiceFactory();
    }
}
