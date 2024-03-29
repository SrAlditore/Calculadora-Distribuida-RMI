package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import modelo.Operacao;

public class ClienteManual {
	private static Socket socket;
	private static ObjectInputStream inMaster;
	private static ObjectOutputStream outMaster;
	
	private static Scanner scan = new Scanner(System.in);
	private static Operacao operacao = new Operacao();
	private static boolean continuar = true;
	private static String resultado = "";
	
	public static void main(String[] args) {

		System.out.println("Cliente Inicializado!");

		try {
			while (continuar) {
				conectarMaster(10000);
				
				lerOperacao();
				
				if (operacao.getError().equals("")) {
					outMaster.writeObject(operacao);
					
					resultado = (String) inMaster.readObject();
					
					System.out.println(resultado);
				} else {
					System.out.println(operacao.getError());
					operacao.setError("");
				}

				desejaSair();
				
				desconectarMaster();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		scan.close();
	}

	/*
	 * Faz a leitura de atributos e Retorna uma opera��o
	 */
	public static void lerOperacao() {
		System.out.println("\nsoma | subtracao | multiplicacao | divisao | raiz | potencia | porcentagem ");
		System.out.println("Informe o operador: ");
		
		String operador = scan.next();

		// Valida se a opera��o selecionada � existente no sistema
		if (validarOperacao(operador.toLowerCase())) {
			operacao.setOperador(operador.toLowerCase());

			try {

				// Testa se a opera��o � uma potencia��o para ler somente 1 numero
				if (operacao.getOperador().equalsIgnoreCase("raiz")) {
					System.out.println("Informe o valor: ");
					operacao.setValor1(Double.parseDouble(scan.next()));
				} else {
					System.out.println("Informe o primeiro valor: ");
					operacao.setValor1(Double.parseDouble(scan.next()));
					System.out.println("Informe o segundo valor: ");
					operacao.setValor2(Double.parseDouble(scan.next()));
				}

			} catch (NumberFormatException e) {
				operacao.setError("\nValor Invalido, Tente Novamente!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			operacao.setError("\nOpera��o Invalida, Tente Novamente!");
		}
	}

	/*
	 * Valida se uma opera��o � valida
	 */
	public static boolean validarOperacao(String op) {
		boolean retorno = false;
		
		switch (op) {
		case "soma":
		case "subtracao":
		case "multiplicacao":
		case "divisao":
		case "raiz":
		case "potencia":
		case "porcentagem":
			retorno = true;
			break;
		default:
			break;
		}

		return retorno;
	}

	/*
	 * Testa se o usuario deseja sair da aplica��o
	 */
	private static void desejaSair() {
		String sair = "";

		do {
			System.out.println("Deseja realizar outra opera��o? S/N");
			sair = scan.next();

			if (sair.equalsIgnoreCase("N"))
				continuar = false;
		} while (!sair.equalsIgnoreCase("S") && !sair.equalsIgnoreCase("N"));
	}

	/*
	 * Conecta o socket na porta do servidor master e cria o input/output
	 */
	public static void conectarMaster(int porta) throws UnknownHostException, IOException {
		System.out.println("\nIniciando conex�o com o servidor. PORTA: " + porta);

		socket = new Socket("localhost", porta);

		inMaster = new ObjectInputStream(socket.getInputStream());
		outMaster = new ObjectOutputStream(socket.getOutputStream());
	}

	/*
	 * Desconecta socket, input e output
	 */
	private static void desconectarMaster() throws IOException {
		if (inMaster != null)
			inMaster.close();

		if (outMaster != null)
			outMaster.close();

		if (socket != null && socket.isConnected())
			socket.close();
	}

}