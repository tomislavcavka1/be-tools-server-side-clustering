package hr.axion.serverside.clustering.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Slf4j
@RequiredArgsConstructor
@Component
public class PoiImportInitializer {

    private final PoiImportInitializerProperties properties;
    private final PoiService poiService;

    @PostConstruct
    public void init() {
        if (properties.isEnabled()) {
            poiService.reimportPoi();
        } else {
            log.info("Skipping POI reimport");
        }
    }
}
