package io.linx.prometheus;


import io.linx.prometheus.matchers.CustomMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.cthul.matchers.CthulMatchers.matchesPattern;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(Application.class)
@WebIntegrationTest(randomPort = true)
public class PrometheusEndpointTest {

    @Value("${local.server.port}")
    int localServerPort;

    RestTemplate template = new TestRestTemplate();

    @Test
    public void testMetricsExportedThroughPrometheusEndpoint() {
        ResponseEntity<String> metricsResponse = template.getForEntity(getBaseUrl() + "/prometheus", String.class);

        assertEquals(HttpStatus.OK, metricsResponse.getStatusCode());
        assertTrue(MediaType.TEXT_PLAIN.isCompatibleWith(metricsResponse.getHeaders().getContentType()));

        List<String> responseLines = Arrays.asList(metricsResponse.getBody().split("\n"));
        assertThat(responseLines, CustomMatchers.exactlyNItems(1,
                matchesPattern("# HELP httpsessions_max httpsessions_max")));
    }

    private String getBaseUrl() {
        return "http://localhost:" + localServerPort;
    }
}
