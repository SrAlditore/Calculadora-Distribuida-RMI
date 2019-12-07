package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import modelo.Operacao;

public class ClienteAutomatico {
	private static Socket socket;
	private static ObjectInputStream inMaster;
	private static ObjectOutputStream outMaster;

	public static void main(String[] args) {
		try {
			boolean continuar = true;
			Operacao operacao = new Operacao();
			String resultado = "";
			int i = 1;

			while (continuar) {
				if ((i % 2) == 0) {
					operacao.setOperador("soma");
					operacao.setValor1(i);
					operacao.setValor2(i);
				} else {
					operacao.setOperador("porcentagem");
					operacao.setValor1(100 * i);
					operacao.setValor2(10);
				}

				conectarMaster();

				outMaster.writeObject(operacao);
				
				resultado = (String) inMaster.readObject();
				
				System.out.println(resultado);

				desconectarMaster();

				continuar = desejaContinuar(continuar, i);

				i++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean desejaContinuar(boolean continuar, int i) {
		if (i % 10 == 0) {
			int resposta = JOptionPane.showConfirmDialog(null, 
							"Deseja que a maquina realize mais 10 calculos?",
							"Deseja Continuar?", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
			
			if (resposta == JOptionPane.NO_OPTION || resposta == JOptionPane.CLOSED_OPTION) {
				JOptionPane.showMessageDialog(null, "Desconectado Com Sucesso!");
				continuar = false;
			}
		}
		return continuar;
	}

	/*
	 * Conecta o socket na porta do servidor master e cria o input/output
	 */
	public static void conectarMaster() throws UnknownHostException, IOException {
		System.out.println("\nIniciando conexão com o servidor. PORTA: 10000");

		socket = new Socket("localhost", 10000);

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