package servidor;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServidorCompostoInterface extends Remote {
	
	public double raiz(double a) throws RemoteException;
	public double potencia(double a, double b) throws RemoteException;
	public double porcentagem(double a, double b) throws RemoteException;
	
}