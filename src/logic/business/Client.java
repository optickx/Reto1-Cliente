package logic.business;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

import exceptions.BadCredentialsException;
import exceptions.NoSuchUserException;
import exceptions.ServerCapacityException;
import exceptions.ServerErrorException;
import exceptions.UserAlreadyExistsException;
import interfaces.Signable;
import java.util.ResourceBundle;

import packets.Request;
import packets.Response;
import packets.User;

import static packets.RequestType.*;

/**
 * This is the implementation for the Signable interface, in charge of
 * communicating with the server
 */
public class Client implements Signable {

    /**
     * The port where the server is listening at
     */
    private static final int PORT = Integer.parseInt(ResourceBundle.getBundle("resources.config").getString("PORT"));

    /**
     * Ip address of the server
     */
    private static final String HOSTNAME = ResourceBundle.getBundle("resources.config").getString("HOSTNAME");

    /**
     * Object with which we will communicate with the server
     */
    private static Socket socket;

    /**
     * Packet sent to the server by the client
     */
    private Request request;

    /**
     * Packet sent by the server
     */
    private Response response;

    /**
     * Open channel to send objects to the server
     */
    ObjectInputStream in;

    /**
     * Channel through where packets from the server will be received
     */
    ObjectOutputStream out;

    @Override
    public User signIn(User user)
            throws BadCredentialsException, NoSuchUserException, ServerCapacityException, ServerErrorException {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(HOSTNAME, PORT), 1000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            // The data is introduced into the request and sent to the server side
            request = new Request(user, LOGIN_REQUEST);
            out.writeObject(request);

            // Receives the response from the server side
            response = (Response) in.readObject();

            return unpackResponse(response);
        } catch (SocketTimeoutException e) {
            throw new ServerErrorException("Server timed out");
        } catch (IOException | ClassNotFoundException e) {
            throw new ServerErrorException("Error connecting to the server");
        } catch (UserAlreadyExistsException e) {
            // this catch clause should never be entered, thus the message
            throw new ServerErrorException("Something went VERY wrong");

        }
    }

    @Override
    public void signUp(User user) throws ServerCapacityException, ServerErrorException, UserAlreadyExistsException {
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(HOSTNAME, PORT), 1000);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            // The data is introduced into the request and sent to the server side
            request = new Request(user, SIGNUP_REQUEST);
            out.writeObject(request);

            // Receives the response from the server side
            response = (Response) in.readObject();

            unpackResponse(response);
        } catch (SocketTimeoutException e) {
            throw new ServerErrorException("Server timed out");
        } catch (IOException | ClassNotFoundException e) {
            throw new ServerErrorException("Error writing to the server");
        } catch (NoSuchUserException | BadCredentialsException e) {
            // this catch clause should never be entered, thus the message
            throw new ServerErrorException("Something went VERY wrong");
        }
    }

    private User unpackResponse(Response res)
            throws ServerErrorException, ServerCapacityException, NoSuchUserException, BadCredentialsException,
            UserAlreadyExistsException {
        switch (res.getResponse()) {
            case BAD_CREDENTIAL_ERROR:
                throw new BadCredentialsException();
            case NO_SUCH_USER_ERROR:
                throw new NoSuchUserException();
            case SERVER_CAPACITY_ERROR:
                throw new ServerCapacityException();
            case SERVER_ERROR:
                throw new ServerErrorException();
            case USER_ALREADY_EXISTS_ERROR:
                throw new UserAlreadyExistsException();
            case OK_RESPONSE:
                return res.getUser();
            default:
                throw new ServerErrorException("Something went wrong");

        }
    }
}
