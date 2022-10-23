package com.collector.rates;

import com.collector.rates.entity.ExchangeRateEntity;
import com.collector.rates.entity.HistoryExchangeRateEntity;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.EnumSet;

@SpringBootApplication
@EnableScheduling
public class RatesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatesApplication.class, args);
	}

//	@EventListener(ApplicationReadyEvent.class)
//	public void generateSqlSchema() {
//		MetadataSources metadataSources = new MetadataSources();
//		metadataSources.addAnnotatedClass(ExchangeRateEntity.class);
//		metadataSources.addAnnotatedClass(HistoryExchangeRateEntity.class);
//		Metadata metadata = metadataSources.buildMetadata();
//
//		SchemaExport schemaExport = new SchemaExport();
//		schemaExport.setFormat(true);
//		schemaExport.setOutputFile("create.sql");
//		schemaExport.createOnly(EnumSet.of(TargetType.SCRIPT), metadata);
//	}
}
