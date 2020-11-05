package stampedlock;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class JefeAlmacen implements Runnable {
	private Almacen almacen;

	public JefeAlmacen(Almacen almacen) {
		
		this.almacen = almacen;
	}

	@Override
	public void run() {
		Producto producto;
		while (!Thread.currentThread().isInterrupted()) {

			try {
				TimeUnit.SECONDS.sleep(2);
				producto=crearProducto();
				almacen.añadirProducto(producto);
				System.out.printf("El producto %d fue añadido a las %s \n",producto.getId()
						,DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM).format(LocalTime.now()));
			} catch (InterruptedException e) {
				
				return;
			}
		}
		
	}

	private Producto crearProducto() {
		
		return new Producto(new Random().nextInt(3)+1);
	}

}
