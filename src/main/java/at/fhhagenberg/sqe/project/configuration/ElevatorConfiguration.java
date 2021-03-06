package at.fhhagenberg.sqe.project.configuration;

import at.fhhagenberg.sqe.project.connection.IElevatorAdapterFactory;
import at.fhhagenberg.sqe.project.connection.rmi.RMIElevatorFactory;
import at.fhhagenberg.sqe.project.services.automatic.IAutomaticModeServiceFactory;
import at.fhhagenberg.sqe.project.services.automatic.advanced.AdvancedAutomaticModeService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

/**
 * This is the Runtime Spring-Configuration of the Elevator Project.
 */
@Configuration
@ComponentScan(basePackages={"at.fhhagenberg.sqe.project"}, excludeFilters={@Filter(type=FilterType.REGEX, pattern="at.fhhagenberg.sqe.mocks.*")})
public class ElevatorConfiguration {

    /**
     * Gets the Elevator Construction Factory to (re)connect.
     *
     * @return A Factory Object to create ElevatorAdapters
     */
    @Bean
    public IElevatorAdapterFactory getIElevatorAdapterFactory() {
        return new RMIElevatorFactory("rmi://localhost/ElevatorSim");
    }

    /**
     * Gets the Automatic Mode Factory to create AutomaticModeServices
     * that contain the Algorithm to drive individual Elevators.
     *
     * @return A Factory Object to create AutomaticModeServices
     */
    @Bean
    public IAutomaticModeServiceFactory getIAutomaticModeServiceFactory() {
        return AdvancedAutomaticModeService::new;
    }
}
