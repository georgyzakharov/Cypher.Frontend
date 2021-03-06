package edu.sunypoly.cypher.frontend.service;

import javax.annotation.PostConstruct;

import org.cypher.commons.AssignmentTest;
import org.cypher.commons.TestRequest;
import org.cypher.commons.TestResponse;
import org.springframework.stereotype.Service;

import edu.sunypoly.cypher.frontend.remote.RemoteApplicationExecutor;
import si.mazi.rescu.RestProxyFactory;

//matches with the request for injection
@Service
public class ApplicationExecutorServiceImpl implements ApplicationExecutorService {
	
	//8081 is the port that my UI application will talk to 
	private String uri = "http://localhost:8081";
	
	private RemoteApplicationExecutor remoteExecutor;
	
	//init (initializing) will create a connection with executor project
	@PostConstruct
	public void init() {
		remoteExecutor = RestProxyFactory.createProxy(RemoteApplicationExecutor.class, uri);
	}
	
	
	//Overrides the finction "getResult" from the IMPL
	@Override
	public String getResult(String language, String applicationCode, String input) {
	
		TestRequest request = new TestRequest();
		request.setApplicationCode(applicationCode);
		request.setLanguage(language);
		
	
		AssignmentTest test = new AssignmentTest();
		test.setTestId(0L);
		test.setInput(input);
		test.setExpectedOutput("");
		
		request.setAssignmentTests(new AssignmentTest[] {test});
		TestResponse testResponse = remoteExecutor.execute(request);
		
		return testResponse.getTestResults()[0].getTestOutput();
	}
	
	

}
