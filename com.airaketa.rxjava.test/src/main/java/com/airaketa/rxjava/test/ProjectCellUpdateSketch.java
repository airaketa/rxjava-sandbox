package com.airaketa.rxjava.test;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class ProjectCellUpdateSketch
{
	private static Random random = new Random();
	
	public static void main(String[] args) throws IOException
	{
		/*
		 * Observable ��� ���������� ���� �� ����
		 * �� ������� �������� � ������ ������� �� presentation-������
		 * (� ����� ������� "������" ����� cell'���)
		 * ��� �������� �� ���� - ���������, ������� �� ���� ����������
		 */
		Observable<Boolean> lockObservable = Observable.create(o -> {
			System.out.println("determine whether the lock file is available");
		    o.onNext(random.nextBoolean());
		});
		
		/*
		 * Observable - ��������� ������. ������ ��� ���������� ����� � ��� �� ��������,
		 * ������������ � Observer'� �������� � ������������� ��������� ����������
		 */
		Observable<Long> scheduler = Observable.interval(0, 5, TimeUnit.SECONDS);
		Disposable subscription = scheduler.subscribe(v -> {
			Disposable fileCheck = lockObservable.subscribe(isLocked -> {
				System.out.println(isLocked ? "file is locked" : "file is not locked");
			});
		});
		
		System.in.read();
	}
}
