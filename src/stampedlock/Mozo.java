package stampedlock;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.concurrent.TimeUnit;

public class Mozo implements Runnable {
	
	private Almacen almacen;
	private int num;

	public Mozo(Almacen almacen, int i) {
		this.almacen = almacen;
		num=i;
	}

	@Override
	public void run() {
	int contador=0;
		
		while(!Thread.currentThread().isInterrupted()) {	
			try {
				TimeUnit.NANOSECONDS.sleep(500);
				
				contador =almacen.consultarStock(num);
				
				System.out.printf("El mozo %d ha consultado el stock y hay %d unidades del producto %d a las %s \n", num,
						contador, num, DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
			} catch (InterruptedException e) {
				
				return;
			}
		}
		
	}

}
