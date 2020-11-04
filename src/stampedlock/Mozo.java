package stampedlock;

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
	
		
		while(!Thread.currentThread().isInterrupted()) {	
			try {
				TimeUnit.NANOSECONDS.sleep(500);
				
				almacen.consultarStock(num);
			} catch (InterruptedException e) {
				
				return;
			}
		}
		
	}

}
