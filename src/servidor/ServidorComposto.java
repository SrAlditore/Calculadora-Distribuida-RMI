package servidor;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ServidorComposto extends UnicastRemoteObject implements ServidorCompostoInterface {
	private static final long serialVersionUID = 1L;

	protected ServidorComposto() throws RemoteException {
		super();
	}

    @Override
	public double raiz(double a) throws RemoteException {
    	System.out.println("Realizando operação de Raiz Quadrada de" + a);
		return Math.sqrt(a);
	}

	@Override
	public double potencia(double a, double b) throws RemoteException {
    	System.out.println("Realizando operação de Potenciação de " + a + ", " + b);
		return Math.pow(a, b);
	}

	@Override
	public double porcentagem(double a, double b) throws RemoteException {
    	System.out.println("Realizando operação de Porcentagem de " + a + ", " + b);
		return (a * b) / 100;
	}

    public static void main(String[] args){
        try {
            Naming.rebind("//localhost/ServidorComposto", new ServidorComposto());            
            System.err.println("Servidor Composto Pronto Para Uso!");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
    
}