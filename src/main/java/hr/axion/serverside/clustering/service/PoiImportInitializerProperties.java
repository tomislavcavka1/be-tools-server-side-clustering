package hr.axion.serverside.clustering.service;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("poi-data-import.initializer")
public class PoiImportInitializerProperties {

    private boolean enabled = true;
}
