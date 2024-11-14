package com.megazone.ERPSystem_phase3_Monolithic.database_config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;



public class DynamicDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContext.getCurrentDataSource();
    }
}
