package com.airaketa.rxjava.test;

import io.reactivex.subjects.PublishSubject;

public class StatusCodeSketch
{
	/*
	 * Observer'ы подписываются заранее. Объект Subject'а всегда один и тот же
	 * после того как в Subject поступает (emit) http статус код, Subject сообщает об этом
	 * всем своим Observer'ам
	 */
	private static PublishSubject<Integer> statusCodeSubject;
	
	public static void main(String[] args)
	{
		PublishSubject<Integer> statusCodeSubject = getStatusCodeSubject();
		statusCodeSubject.subscribe(code -> {
			/*
			 * если код 401 - обновляем токен
			 */
			if (code == 401)
			{
				System.out.println("refresh token");
			}
		});

		makeRequests();
	}

	public static PublishSubject<Integer> getStatusCodeSubject()
	{
		statusCodeSubject =  PublishSubject.create();
		return statusCodeSubject;
	}
	
	private static void makeRequests()
	{
		/*
		 * "излучение" данных для Observer'ов
		 */
		statusCodeSubject.onNext(200);
		statusCodeSubject.onNext(200);
		statusCodeSubject.onNext(200);
		statusCodeSubject.onNext(401);
	}
}
