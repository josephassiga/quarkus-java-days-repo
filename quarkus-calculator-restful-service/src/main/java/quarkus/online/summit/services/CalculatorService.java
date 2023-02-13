package quarkus.online.summit.services;

import javax.enterprise.context.ApplicationScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class CalculatorService {

    final Logger LOG = LoggerFactory.getLogger(CalculatorService.class);

    public Float add(final String left, final String rigth) {
        LOG.info("Adding {} to {}", left, rigth);
        return Float.parseFloat(left) + Float.parseFloat(rigth);
    }

    public Float multiply(final String left, final String rigth) {
        LOG.info("Multiplying {} to {}", left, rigth);
        return Float.parseFloat(left) * Float.parseFloat(rigth);
    }
}
