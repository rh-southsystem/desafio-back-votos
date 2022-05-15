package com.southsystem.config;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.southsystem.domain.Assembly;
import com.southsystem.domain.enums.AssemblyStatus;
import com.southsystem.service.AssemblyService;

@Configuration
@EnableScheduling
public class AssemblyScheduleConfig {
	
	private static final Integer PAGE = 0;
	private static final Integer LINES_PER_PAGE = 10;
	private static final String ORDER_BY = "startDate";
	private static final String DIRECTION = "ASC";
	
	@Autowired
	private AssemblyService assemblyService;

	@Scheduled(fixedDelay = 60000)
	public void verifyStartedAssemblies() {
	    Page<Assembly> startedAssemblies = assemblyService.listByStatus(PAGE, LINES_PER_PAGE,
	    		ORDER_BY, DIRECTION, AssemblyStatus.STARTED);
	    
	    startedAssemblies.forEach(assembly -> {
	    	LocalDateTime endTime = assembly.getStartDate().plus(assembly.getDuration(), ChronoUnit.MILLIS);
	    	if (LocalDateTime.now().isAfter(endTime)) {
	    		assemblyService.finishVoting(assembly);
	    	}
	    });
	}
	
}
