package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;

import modelo.Operacao;

public class ServidorMaster extends Thread {
	private static ServerSocket serverSocket;
	private Socket socket;

	private ServidorBasicoInterface servidorBasico;
	private ServidorCompostoInterface servidorComposto;

	public ServidorMaster(Socket s) {
		this.socket = s;
	}

	public void run() {
		try {
			ObjectOutputStream outCliente = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream inCliente = new ObjectInputStream(socket.getInputStream());

			servidorBasico = (ServidorBasicoInterface) Naming.lookup("//localhost/ServidorBasico");
			servidorComposto = (ServidorCompostoInterface) Naming.lookup("//localhost/ServidorComposto");
			
			Operacao operacao = (Operacao) inCliente.readObject(); // Recebe Operação do Client
			operacao = realizarOperacao(operacao);
			
			if (operacao.getOperador().equalsIgnoreCase("raiz")) {
				outCliente.writeObject(operacao.toStringRaiz()); // Manda Operação Resolvida
			} else {
				outCliente.writeObject(operacao.toString()); // Manda Operação Resolvida
			}

			outCliente.close();
			inCliente.close();
			socket.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Operacao realizarOperacao(Operacao operacao) throws RemoteException {
		switch (operacao.getOperador()) {
			case "soma":
			case "subtracao":
			case "multiplicacao":
			case "divisao":
				operacao = realizarOperacaoBasica(operacao);
				break;
			case "potencia":
			case "porcentagem":
			case "raiz":
				operacao = realizarOperacaoComposta(operacao);
				break;
			default:
				break;
		}
		return operacao;
	}

	private Operacao realizarOperacaoBasica(Operacao operacao) throws RemoteException {
		switch (operacao.getOperador()) {
			case "soma":
				operacao.setResultado(servidorBasico.somar(operacao.getValor1(), operacao.getValor2()));
				break;
			case "subtracao":
				operacao.setResultado(servidorBasico.subtrair(operacao.getValor1(), operacao.getValor2()));
				break;
			case "multiplicacao":
				operacao.setResultado(servidorBasico.multiplicar(operacao.getValor1(), operacao.getValor2()));
				break;
			case "divisao":
				operacao.setResultado(servidorBasico.dividir(operacao.getValor1(), operacao.getValor2()));
				break;
			default:
				operacao.setResultado(MAX_PRIORITY);
				break;
		}
		
		return operacao;
	}
	
	private Operacao realizarOperacaoComposta(Operacao operacao) throws RemoteException {
		switch (operacao.getOperador()) {
			case "raiz":
				operacao.setResultado(servidorComposto.raiz(operacao.getValor1()));
				break;
			case "potencia":
				operacao.setResultado(servidorComposto.potencia(operacao.getValor1(), operacao.getValor2()));
				break;
			case "porcentagem":
				operacao.setResultado(servidorComposto.porcentagem(operacao.getValor1(), operacao.getValor2()));
				break;
			default:
				operacao.setResultado(MAX_PRIORITY);
				break;
		}
		
		return operacao;
	}

	public static void main(String[] args) {
		try {
			serverSocket = new ServerSocket(10000);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		System.out.println("Server Master rodando na porta = " + serverSocket.getLocalPort());

		while (true) {
			try {
				Socket conexao = serverSocket.accept();

				System.out.println("\n======================================");
				System.out.println("\nCliente Aceito");
				System.out.println("HOSTNAME = " + conexao.getInetAddress().getHostName());
				System.out.println("HOST ADDRESS = " + conexao.getInetAddress().getHostAddress());
				System.out.println("PORTA LOCAL = " + conexao.getLocalPort());
				System.out.println("PORTA DE CONEXAO = " + conexao.getPort());

				new ServidorMaster(conexao).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}