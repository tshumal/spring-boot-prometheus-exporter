package io.linx.prometheus;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.spring.boot.SpringBootMetricsCollector;
import org.springframework.boot.actuate.autoconfigure.ManagementContextConfiguration;
import org.springframework.boot.actuate.condition.ConditionalOnEnabledEndpoint;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;

import java.util.Collection;

@ManagementContextConfiguration
public class PrometheusConfiguration {

  @Bean
  PrometheusEndpoint prometheusEndpoint(CollectorRegistry registry) {
    return new PrometheusEndpoint(registry);
  }

  @Bean
  @ConditionalOnBean(PrometheusEndpoint.class)
  @ConditionalOnEnabledEndpoint("prometheus")
  PrometheusMvcEndpoint prometheusMvcEndpoint(PrometheusEndpoint prometheusEndpoint) {
    return new PrometheusMvcEndpoint(prometheusEndpoint);
  }

  @Bean
  public CollectorRegistry collectorRegistry() {
    return new CollectorRegistry();
  }

  @Bean
  public SpringBootMetricsCollector metricsCollector(final Collection<PublicMetrics> metrics, final CollectorRegistry registry) {
    return new SpringBootMetricsCollector(metrics).register(registry);
  }

}
