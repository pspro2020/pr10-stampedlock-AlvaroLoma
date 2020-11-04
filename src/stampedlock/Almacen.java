package stampedlock;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import java.util.concurrent.locks.StampedLock;

public class Almacen implements Runnable {
	
	private List<Producto> stock= new ArrayList<Producto>();
	private final StampedLock stampedLock= new StampedLock();
	
	
//	public Almacen() {
//		//primerosProductos();
//	}
//	private void primerosProductos() {
//		for (int i = 0; i < 5; i++) {
//			stock.add(crearProducto());
//		}
//		
//	}
	@Override
	public void run() {
		while (!Thread.currentThread().isInterrupted()) {

			try {
				TimeUnit.SECONDS.sleep(2);
				añadirProducto(crearProducto());
			} catch (InterruptedException e) {
				
				return;
			}
		}
		
		
	}
	private void añadirProducto(Producto producto) {
		long stamp=stampedLock.writeLock();
		try {
			
			stock.add(producto);
			System.out.printf("El producto %d fue añadido a las %s \n",producto.getId()
					,DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
		} finally {
			stampedLock.unlockWrite(stamp);
		}
		
	}
	private Producto crearProducto() {
		
		return new Producto(new Random().nextInt(3)+1);
	}

	public void consultarStock(int num) {
		int contador = 0;
		long stamp = stampedLock.tryOptimisticRead();

		for (Producto producto : stock) {
			if (producto.getId() == num) {
				contador++;
			}
		}

		if (!stampedLock.validate(stamp)) {

			stamp = stampedLock.readLock();
			try {

				for (Producto producto : stock) {
					if (producto.getId() == num) {
						contador++;
					}
				}

			} finally {

				stampedLock.unlockRead(stamp);

			}
		}
		System.out.printf("El mozo %d ha consultado el stock y hay %d unidades del producto %d a las %s \n", num,
				contador, num, DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));

	}
}
