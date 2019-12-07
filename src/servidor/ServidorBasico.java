package servidor;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServidorBasico extends UnicastRemoteObject implements ServidorBasicoInterface {
	private static final long serialVersionUID = 1L;

	protected ServidorBasico() throws RemoteException {
		super();
	}

	@Override
	public double somar(double a, double b) throws RemoteException {
    	System.out.println("Realizando opera��o de Adi��o de " + a + ", " + b);
		return a + b;
	}

	@Override
	public double subtrair(double a, double b) throws RemoteException {
    	System.out.println("Realizando opera��o de Potencia��o de " + a + ", " + b);
		return a - b;
	}

	@Override
	public double multiplicar(double a, double b) throws RemoteException {
    	System.out.println("Realizando opera��o de Multiplica��o de " + a + ", " + b);
		return a * b;
	}

	@Override
	public double dividir(double a, double b) throws RemoteException {
    	System.out.println("Realizando opera��o de Divis�o de " + a + ", " + b);
		return a / b;
	}

	public static void main(String[] args) {
		try {
			Naming.rebind("//localhost/ServidorBasico", new ServidorBasico());
            System.err.println("Servidor Basico Pronto Para Uso!");
		} catch (Exception e) {
			System.err.println("Server exception: " + e.toString());
			e.printStackTrace();
		}
	}

}