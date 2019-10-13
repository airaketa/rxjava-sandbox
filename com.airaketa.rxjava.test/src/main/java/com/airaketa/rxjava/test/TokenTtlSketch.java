package com.airaketa.rxjava.test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;

/**
 * 
 * ������ �� ����� ����� ������, �� ��������� �������� ������ ����������� ����������
 *
 */
public class TokenTtlSketch
{
	public static void main(String[] args) throws IOException
	{
		long w = System.currentTimeMillis() + 500;
		Observable<Long> tokenObservable = createTokenTtlObservable(w);
		
		tokenObservable.subscribe(v -> {
			System.out.println("token refresh");
		});
		
		/**
		 *
		 * ����������:
		 * 
		 *  - ����� ����, ��� ������ observable ��������, observable ����� ������������� ������
		 *    � ����� ��������, �������������� ����� ����� ������ ��������� �� ���� ���� observer'��
		 *  
		 *  - ������ ������������� ��� �� observable (���� ����� interval, ������� ������������� ��������,
		 *    �� ��� �������� ������� �������� ������ ���� ��� �� ����� ��������
		 *  
		 *  - �� ��������� ������� observable ������ �� ���� ����������� 0L.
		 *    ��������� observable ���, ����� �� ��������� ���-�� ������ (�������� ��� �����) - ������.
		 * �����������:
		 * 
		 *  - ������ ��� ������� Observer'� ����
		 */
		
		System.in.read();
	}
	
	private static Observable<Long> createTokenTtlObservable(Long validUntil)
	{
		long toWait = validUntil - System.currentTimeMillis();
		return Observable.timer(toWait, TimeUnit.MILLISECONDS);
	}
}
