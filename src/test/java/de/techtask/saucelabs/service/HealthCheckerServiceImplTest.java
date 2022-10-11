package de.techtask.saucelabs.service;

import de.techtask.saucelabs.model.AggregatedAppHealthStatusCode;
import de.techtask.saucelabs.model.AppHealth;
import de.techtask.saucelabs.model.AppHealthStatusCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class HealthCheckerServiceImplTest {

    @Autowired
    private HealthCheckerServiceImpl healthCheckerService;

    @Test
    public void testGetAggregatedHealthStatusAsReachable() {
        // given
        AppHealth[] appHealths = new AppHealth[] {
                new AppHealth(AppHealthStatusCode.OK, LocalDateTime.now().minusSeconds(15), false),
                new AppHealth(AppHealthStatusCode.ERROR, LocalDateTime.now().minusSeconds(10), false),
                new AppHealth(AppHealthStatusCode.NOT_REACHABLE, LocalDateTime.now().minusSeconds(5), false)
        };
        List<AppHealth> appHealthList = Arrays
                .stream(appHealths)
                .collect(Collectors.toList());

        // when
        AggregatedAppHealthStatusCode aggregatedAppHealthStatusCode = healthCheckerService.getAggregatedHealthStatusCode(appHealthList);

        // then
        Assertions.assertEquals(AggregatedAppHealthStatusCode.REACHABLE, aggregatedAppHealthStatusCode);
    }

    @Test
    public void testGetAggregatedHealthStatusAsNotReachable() {
        // given
        AppHealth[] appHealths = new AppHealth[] {
                new AppHealth(AppHealthStatusCode.NOT_REACHABLE, LocalDateTime.now().minusSeconds(15), false),
                new AppHealth(AppHealthStatusCode.NOT_REACHABLE, LocalDateTime.now().minusSeconds(10), false),
                new AppHealth(AppHealthStatusCode.NOT_REACHABLE, LocalDateTime.now().minusSeconds(5), false)
        };
        List<AppHealth> appHealthList = Arrays
                .stream(appHealths)
                .collect(Collectors.toList());

        // when
        AggregatedAppHealthStatusCode aggregatedAppHealthStatusCode = healthCheckerService.getAggregatedHealthStatusCode(appHealthList);

        // then
        Assertions.assertEquals(AggregatedAppHealthStatusCode.NOT_REACHABLE, aggregatedAppHealthStatusCode);
    }

}
