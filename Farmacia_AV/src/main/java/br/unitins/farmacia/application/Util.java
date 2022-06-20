package br.unitins.farmacia.application;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;

import org.apache.commons.codec.digest.DigestUtils;
import br.unitins.farmacia.model.Usuario;

public class Util {
	
	public static void main(String[] args) {
		String senha = "janiojunior" + "123";
		senha = Util.hash(senha);
		System.out.println(senha);
		System.out.println(Util.hash("123"));
	}
	
	public static String hash(String valor) {
		return DigestUtils.sha256Hex(valor);
	}
	
	public static String hash(Usuario usuario) {
		return DigestUtils.sha256Hex(usuario.getLogin()+usuario.getSenha());
	}

	public static void addMessageInfo(String message) {
		addMessage(message, FacesMessage.SEVERITY_INFO);
	}

	public static void addMessageWarn(String message) {
		addMessage(message, FacesMessage.SEVERITY_WARN);
	}

	public static void addMessageError(String message) {
		addMessage(message, FacesMessage.SEVERITY_ERROR);
	}

	private static void addMessage(String message, Severity severity) {
		FacesMessage fm = new FacesMessage(severity, message, null);
		FacesContext.getCurrentInstance().addMessage(null, fm);
	}
	
	public static void redirect(String page) {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect(page);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
