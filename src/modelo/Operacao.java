package modelo;

import java.io.Serializable;

public class Operacao implements Serializable {

	private static final long serialVersionUID = 1L;

	private String operador;
	private double valor1;
	private double valor2;
	private double resultado;
	private String error = "";

	public Operacao() {

	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public double getValor1() {
		return valor1;
	}

	public void setValor1(double valor1) {
		this.valor1 = valor1;
	}

	public double getValor2() {
		return valor2;
	}

	public void setValor2(double valor2) {
		this.valor2 = valor2;
	}

	public double getResultado() {
		return resultado;
	}

	public void setResultado(double resultado) {
		this.resultado = resultado;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String toString() {
		return "\nOperacação: " + this.operador + "\nValor 1: " + this.valor1 + "\nValor 2: " + this.valor2
				+ "\nResultado: " + this.resultado + "\n";
	}

	public String toStringRaiz() {
		return "\nOperação: " + this.operador + "\nValor: " + this.valor1 + "\nResultado: " + this.resultado + "\n";
	}
}