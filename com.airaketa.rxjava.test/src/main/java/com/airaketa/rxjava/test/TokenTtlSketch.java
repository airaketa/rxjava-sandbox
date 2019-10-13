package com.airaketa.rxjava.test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;

/**
 * 
 * Таймер на время жизни токена, по истечению которого должно происходить обновление
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
		 * Недостатки:
		 * 
		 *  - после того, как таймер observable истекает, observable нужно пересоздавать заново
		 *    с новым временем, соответственно нужно также заново назначать на него всех observer'ов
		 *  
		 *  - нельзя перезапустить тот же observable (есть метод interval, который функционирует циклично,
		 *    но там интервал времени задается только один раз во время создания
		 *  
		 *  - по истечению таймера observable кидает по сути бесполезную 0L.
		 *    Настроить observable так, чтобы он выкидывал что-то другое (например сам токен) - нельзя.
		 * Особенности:
		 * 
		 *  - отсчет для каждого Observer'а свой
		 */
		
		System.in.read();
	}
	
	private static Observable<Long> createTokenTtlObservable(Long validUntil)
	{
		long toWait = validUntil - System.currentTimeMillis();
		return Observable.timer(toWait, TimeUnit.MILLISECONDS);
	}
}
