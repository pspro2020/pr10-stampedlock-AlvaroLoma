package stampedlock;

import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) {
		Almacen almacen =new Almacen();
	
		Thread hilosMozos[]= new Thread[3];
		Thread hiloAlmacen= new Thread(almacen);
		hiloAlmacen.start();
		for (int i = 0; i < 3; i++) {
			hilosMozos[i]= new Thread(new Mozo(almacen,i+1));
		}
		for (int i = 0; i < 3; i++) {
			hilosMozos[i].start();
		}
		//CODIGO PARA INTERRUMPIR LOS HILOS SECUNDARIOS Y FINALIZAR EL PROGRAMA
		try {
			TimeUnit.MINUTES.sleep(1);
			
			hiloAlmacen.interrupt();
			for (int i = 0; i < hilosMozos.length; i++) {
				hilosMozos[i].interrupt();
			}
			System.out.println("Fin del programa");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
