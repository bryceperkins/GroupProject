package client.login;

import client.base.*;
import client.misc.*;
import client.server.*;
import client.model.*;

import shared.commands.*;

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;


/**
 * Implementation for the login controller
 */
public class LoginController extends Controller implements ILoginController {

	private IMessageView messageView;
	private IAction loginAction;
    private GameManager manager;
	
	/**
	 * LoginController constructor
	 * 
	 * @param view Login view
	 * @param messageView Message view (used to display error messages that occur during the login process)
	 */
	public LoginController(ILoginView view, IMessageView messageView) {

		super(view);
		
		this.messageView = messageView;
	}
	
	public ILoginView getLoginView() {
		
		return (ILoginView)super.getView();
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	
	/**
	 * Sets the action to be executed when the user logs in
	 * 
	 * @param value The action to be executed when the user logs in
	 */
	public void setLoginAction(IAction value) {
		
		loginAction = value;
	}
	
	/**
	 * Returns the action to be executed when the user logs in
	 * 
	 * @return The action to be executed when the user logs in
	 */
	public IAction getLoginAction() {
		return loginAction;
	}

	@Override
	public void start() {
		getLoginView().showModal();
	}

    private void error(String message) {
        this.messageView.setTitle("Error");
        this.messageView.setMessage(message);
        this.messageView.setAction(new IAction() {
            @Override
            public void execute()
            {
                start();
            }
        });
        this.messageView.showModal();
    }

	@Override
	public void signIn() {
        String username = getLoginView().getLoginUsername();
        String password = getLoginView().getLoginPassword();
        String response = "";
        getLoginView().closeModal();
        try {
            response = this.manager.getServer().execute(new UserLogin(username, password));

            if (!response.equals("Success")){
                error("Failed to login.");
            } else {
                loginAction.execute();
            }
        } catch (NullPointerException e) {
            error("Invalid username or password");
        }

	}

	@Override
	public void register() {
        String username = getLoginView().getRegisterUsername();
        String pass1 = getLoginView().getRegisterPassword();
        String pass2 = getLoginView().getRegisterPasswordRepeat();
        String response = "";

        getLoginView().closeModal();
        if (pass1.equals(pass2)) {
            try {
                response = this.manager.getServer().execute(new UserRegister(username, pass1));
                if (!response.equals("Success")){
                    error("Failed to register user");
                }
                else {
                    loginAction.execute();
                }
            } catch (NullPointerException e) {
                error("Invalid username or password");
            }
        }
        else {
            error("Passwords do not match");
        }
	}

    public void setManager(GameManager manager){
        this.manager = manager;
    }

}

