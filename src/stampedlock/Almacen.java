package stampedlock;


import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import java.util.concurrent.locks.StampedLock;

public class Almacen {
	
	private List<Producto> stock= new ArrayList<Producto>();
	private final StampedLock stampedLock= new StampedLock();

	public void añadirProducto(Producto producto) {
		long stamp=stampedLock.writeLock();
		try {
			
			stock.add(producto);
			
		} finally {
			stampedLock.unlockWrite(stamp);
		}
		
	}
	

	public int consultarStock(int num) {
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
		return contador;

	}
}
