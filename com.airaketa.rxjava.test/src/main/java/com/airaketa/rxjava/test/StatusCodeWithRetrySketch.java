package com.airaketa.rxjava.test;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

public class StatusCodeWithRetrySketch
{
	/*
	 * Subject, Observer которого после "излучения" идентификатора операции, выполняет ее
	 * (запрос проектов) 
	 */
	private static PublishSubject<Operation> projectTypesSubject;
	
	/*
	 * Subject "испускает" данные только в случае, если токен протух.
	 * Данные - Enum http запроса, при котором произошло протухание
	 * данный Subject хранит только одно, последнее значение
	 */
	private static BehaviorSubject<Operation> statusCodeSubject;
	
	public static void main(String[] args)
	{
		BehaviorSubject<Operation> statusCodeSubject = getStatusCodeSubject();
		statusCodeSubject.subscribe(previousOperation -> {
			System.out.println("token refresh attempt");
			getProjectTypesSubject().onNext(previousOperation);
		});

		makeRequests();
	}

	public static BehaviorSubject<Operation> getStatusCodeSubject()
	{
		statusCodeSubject =  BehaviorSubject.create();
		return statusCodeSubject;
	}
	
	public static PublishSubject<Operation> getProjectTypesSubject()
	{
		projectTypesSubject = PublishSubject.create();
		projectTypesSubject.subscribe(previousOperation -> {
			switch(previousOperation)
			{
			case GET_PROJECTS:
				System.out.println("project types update attempt");
				break;
			default:
			}
		});
		
		return projectTypesSubject;
	}
	
	private static void makeRequests()
	{
		//request projects, suddenly 401 occurs
		System.out.println("token is expired");
		statusCodeSubject.onNext(Operation.GET_PROJECTS);
	}
	
	private enum Operation
	{
		GET_PROJECTS
	}
}
