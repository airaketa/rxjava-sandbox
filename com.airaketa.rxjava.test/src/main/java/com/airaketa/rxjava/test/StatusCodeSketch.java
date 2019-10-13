package com.airaketa.rxjava.test;

import io.reactivex.subjects.PublishSubject;

public class StatusCodeSketch
{
	/*
	 * Observer'� ������������� �������. ������ Subject'� ������ ���� � ��� ��
	 * ����� ���� ��� � Subject ��������� (emit) http ������ ���, Subject �������� �� ����
	 * ���� ����� Observer'��
	 */
	private static PublishSubject<Integer> statusCodeSubject;
	
	public static void main(String[] args)
	{
		PublishSubject<Integer> statusCodeSubject = getStatusCodeSubject();
		statusCodeSubject.subscribe(code -> {
			/*
			 * ���� ��� 401 - ��������� �����
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
		 * "���������" ������ ��� Observer'��
		 */
		statusCodeSubject.onNext(200);
		statusCodeSubject.onNext(200);
		statusCodeSubject.onNext(200);
		statusCodeSubject.onNext(401);
	}
}
