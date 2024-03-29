package telecomlab3.commands;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import telecomlab3.Callback;
import telecomlab3.CommHandler;
import telecomlab3.Command;
import telecomlab3.Message;
import telecomlab3.User;

/**
 * Represents the delete user command. User will be deleted from the server.
 */
public class DeleteCommand implements Command, Callback {

    private final String name = "delete";
    private final int argCount = 0;

    private static final Logger logger = Logger.getLogger(DeleteCommand.class.getName());

    private final CommHandler comm;
    private final User user;

    /**
     * Initializes the command.
     *
     * @param comm The {@link CommHandler CommHanlder} to use when sending
     * messages.
     * @param user The {@link User User} to use for representing the current
     * user.
     */
    public DeleteCommand(CommHandler comm, User user) {
        this.comm = comm;
        this.user = user;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void execute(String arguments) {
        if (user == null) {
            System.out.println("Error: user not logged in.");
        } else if (user != null && !user.getLoginState()) {
            System.out.println("Error: user not logged in.");
        } else {
            try {
                // We send a string with a space (not an emtpy string) because
                // the server never responds if we send an empty string
                comm.sendMessage(new Message(Message.TYPE_DELETE_USER, " "), this);
            } catch (UnsupportedEncodingException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public int getArgCount() {
        return argCount;
    }

    @Override
    // Message always of type delete
    public void handleResponse(Message msg) {
        if (msg.getSubType() == Message.SUBTYPE_DELETE_USER_SUCCESS) {
            // User deleted, set our user object to reflect that
            user.setLogin(false);
            user.setUsername(null);
            user.setPassword(null);
        } else if (msg.getSubType() == Message.SUBTYPE_DELETE_USER_NOT_LOG_IN) {
            // User was not logged in, so clearly our user object is not in sync
            // with the server - correct that.
            user.setLogin(false);
        }
        System.out.println(msg.getDataAsString());
    }

}
