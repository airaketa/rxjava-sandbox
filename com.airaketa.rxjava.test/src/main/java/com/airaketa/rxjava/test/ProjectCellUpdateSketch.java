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
		 * Observable для вычисления лока на файл
		 * по задумке кладется в объект проекта на presentation-уровне
		 * (и таким образом "гуляет" между cell'ами)
		 * при подписке на него - вычисляет, залочен ли файл воркспейса
		 */
		Observable<Boolean> lockObservable = Observable.create(o -> {
			System.out.println("determine whether the lock file is available");
		    o.onNext(random.nextBoolean());
		});
		
		/*
		 * Observable - цикличный таймер. Служит для выполнения одной и той же операции,
		 * определенной в Observer'е циклично с фикисрованным временным интервалом
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
