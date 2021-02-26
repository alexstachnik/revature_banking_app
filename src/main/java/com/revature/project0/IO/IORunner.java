package com.revature.project0.IO;

import java.io.IOException;
import java.util.List;

import com.revature.project0.core.AccountBalance;
import com.revature.project0.core.BankLoginException;
import com.revature.project0.core.BankRegisterException;
import com.revature.project0.core.BankSQLException;
import com.revature.project0.core.BankStateException;
import com.revature.project0.core.CoreState;
import com.revature.project0.core.Password;
import com.revature.project0.core.UserObject;
import com.revature.project0.db.DBInterface;

public class IORunner {

	private CoreState state;
	private UserIO userio;
	
	public CoreState getState() {
		return state;
	}
	
	public void setState(CoreState state) {
		this.state=state;
	}
	
	public IORunner(DBInterface db) {
		state = new CoreState(db);
		this.userio=null;
	}
	
	static void assertArgs(List<Argument> args,String argFmtString) throws CommandException {
		String[] fmtStrings = argFmtString.split(",");
		if (args.size() > fmtStrings.length) {
			throw new CommandException("Too many arguments");
		}
		for (int i=0;i<fmtStrings.length;++i) {

			if (i >= args.size()) {
				throw new CommandException("Not enough arguments");
			}
			
			if (fmtStrings[i].equals("string")) {
				if (!args.get(i).isStringArg()) {
					throw new CommandException("Not a string: "+args.get(i));
				}
				if (args.get(i).getStringArg().trim().equals("")) {
					throw new CommandException("Empty argument");
				}
			} if (fmtStrings[i].equals("int")) {
				if (!args.get(i).isIntArg()) {
					throw new CommandException("Not an integer: "+args.get(i));
				}			
			} if (fmtStrings[i].equals("double")) {
				if (!args.get(i).isFloatArg()) {
					throw new CommandException("Not a float: "+args.get(i));
				}			
			}
		}
	}
	
	public void run(UserIO userio) {
		while (true) {
			try {
				this.userio=userio;
				Command c = userio.getCommand();
				
				switch(c.getCommandType()) {
				case COMMAND_CLOSE:
					this.state.logout();
					userio.sendSuccess("Goodbye");
					return;
				case INVALID_COMMAND:
					throw new CommandException("Invalid command");
				case CREATE_ACCOUNT_COMMAND:
					try {
						createAccount(c.getArgs());
					} catch (CommandException e) {
						throw new CommandException("Expected syntax: create_account <account name>");
					}
					break;
				case LOGIN_COMMAND:
					try {
						login(c.getArgs());
					} catch (CommandException e) {
						throw new CommandException("Expected syntax: login <username> <password>",e);
					}
					break;
				case LOGOUT_COMMAND:
					if (state.isLoggedIn()) {
						state.logout();
						userio.sendSuccess("Logged out");
					} else {
						userio.printException(new BankStateException("Already logged out"));
					}					
					break;
				case REGISTER_SUPERUSER_COMMAND:
					try {
						registerSuperuser(c.getArgs());
					} catch (CommandException e) {
						throw new CommandException("Expected syntax: register_superuser <username> <password>",e);
					}
					break;
				case NULL_COMMAND:
					break;
				case REGISTER_COMMAND:
					try {
						register(c.getArgs());
					} catch (CommandException e) {
						throw new CommandException("Expected syntax: register <username> <password>",e);
					}
					break;
				case VIEW_USERS_COMMAND:
					viewUsers(c.getArgs());
					break;
				case UPDATE_USER_COMMAND:
					try {
						updateUser(c.getArgs());
					} catch (CommandException e) {
						throw new CommandException("Expected syntax: update_user <user name> <new password> <superuser: true|false>",e);
					}
					break;
				case DELETE_USER_COMMAND:
					try {
						deleteUser(c.getArgs());
					} catch (CommandException e) {
						throw new CommandException("Expected syntax: delete_user <user name>",e);
					}
					break;
				case BALANCE_COMMAND:
					showBalance(c.getArgs());
					break;
				case DELETE_ACCOUNT_COMMAND:
					try {
						deleteAccount(c.getArgs());
					} catch (CommandException e) {
						throw new CommandException("Expected syntax: delete_account <account name>",e);
					}
					break;
				case DEPOSIT_COMMAND:
					try {
						deposit(c.getArgs());
					} catch (CommandException e) {
						throw new CommandException("Expected syntax: deposit <account> <amount>",e);
					}
					break;
				case WITHDRAWL_COMMAND:
					try {
						withdrawal(c.getArgs());
					} catch (CommandException e) {
						throw new CommandException("Expected syntax: withdraw <account> <amount>",e);
					}
					break;
				case VIEW_ACCOUNTS_COMMAND:
					viewAllAccounts();
				}
			} catch (IOException e) {
				userio.printException(e);
				break;
			} catch (BankStateException e) {
				userio.printException(e);
			} catch (BankSQLException e) {
				userio.sendFatalException(e);
			} catch (BankLoginException e) {
				userio.printException(e);
			} catch (CommandException e) {
				userio.printException(e);
			} catch (BankRegisterException e) {
				userio.printException(e);
			}
			
		}
	}
	
	private void withdrawal(List<Argument> args) throws CommandException, BankSQLException, BankStateException {
		assertArgs(args,"string,float");
		String accountName = args.get(0).getStringArg();
		double withdrawalAmt = args.get(1).getFloatArg();
		this.state.withdrawal(accountName,withdrawalAmt);
		this.userio.sendSuccess("Made withdrawal for "+Double.toString(withdrawalAmt)+" from "+accountName);
	}

	private void deposit(List<Argument> args) throws CommandException, BankSQLException, BankStateException {
		assertArgs(args,"string,float");
		String accountName = args.get(0).getStringArg();
		double depositAmt = args.get(1).getFloatArg();
		this.state.deposit(accountName,depositAmt);
		this.userio.sendSuccess("Made deposit for "+Double.toString(depositAmt)+" to account "+accountName);
	}

	private void deleteAccount(List<Argument> args) throws CommandException, BankSQLException, BankStateException {
		assertArgs(args,"string");
		String accountName = args.get(0).getStringArg();
		this.state.deleteAccount(accountName);
		this.userio.sendSuccess("Deleted account "+accountName);
	}
	
	private void createAccount(List<Argument> args) throws CommandException, BankSQLException, BankStateException {
		assertArgs(args,"string");
		String accountName = args.get(0).getStringArg();
		this.state.createAccount(accountName);
		this.userio.sendSuccess("Created account "+accountName);
	}

	private void viewAllAccounts() throws BankStateException, BankSQLException {
		List<AccountBalance> balances = this.state.getAllBalances();
		
		this.userio.sendLine("Current balances:");
		this.userio.sendLine("Account name\t\tBalance\t\tUser name");
		this.userio.sendLine("-------------------------");
		this.userio.sendDebug("BEGIN LIST");
		for (AccountBalance balance : balances) {
			String accountBalanceString = String.format(java.util.Locale.US,"%.2f", balance.getAccountBalance()); 
			this.userio.sendLine(balance.getAccountName()+"\t\t\t"+accountBalanceString+"\t\t"+balance.getUsername());
			this.userio.sendDebug(balance.getAccountName()+"\t"+accountBalanceString+"\t"+balance.getUsername());
		}
		this.userio.sendDebug("END LIST");
	}
	
	private void showBalance(List<Argument> args) throws CommandException, BankStateException, BankSQLException {
		assertArgs(args,"");
		
		List<AccountBalance> balances = this.state.getAllMyBalances();
		
		this.userio.sendLine("Current balances:");
		this.userio.sendLine("Account name\t\tBalance");
		this.userio.sendLine("-------------------------");
		this.userio.sendDebug("BEGIN LIST");
		for (AccountBalance balance : balances) {
			String accountBalanceString = String.format(java.util.Locale.US,"%.2f", balance.getAccountBalance()); 
			this.userio.sendLine(balance.getAccountName() + "\t\t\t" + accountBalanceString);
			this.userio.sendDebug(balance.getAccountName()+"\t"+accountBalanceString);
		}
		this.userio.sendDebug("END LIST");
	}

	private void deleteUser(List<Argument> args) throws CommandException, BankSQLException, BankStateException {
		assertArgs(args,"string");
		String username = args.get(0).getStringArg();
		this.state.deleteUser(username);
		this.userio.sendSuccess("User deleted");
	}

	private void updateUser(List<Argument> args) throws CommandException, BankSQLException, BankStateException {
		assertArgs(args,"string,string,string");
		String username = args.get(0).getStringArg();
		String password = args.get(1).getStringArg();
		String superuser = args.get(2).getStringArg();
		boolean isSuperuser;
		if (superuser.equals("true")) {
			isSuperuser=true;
		} else if (superuser.equals("false")) {
			isSuperuser=false;
		} else {
			throw new CommandException("Specify whether user is superuser");
		}
		this.state.updateUser(username,Password.hash(password),isSuperuser);
		this.userio.sendSuccess("User updated");
	}

	private void viewUsers(List<Argument> args) throws CommandException, BankStateException, BankSQLException {
		List<UserObject> users = this.state.getUsers();
		this.userio.sendDebug("BEGIN LIST");
		for (UserObject user : users) {
			this.userio.sendLine(user.getUsername());
			this.userio.sendDebug(user.getUsername());
		}
		this.userio.sendDebug("END LIST");
	}

	private void login(List<Argument> args) throws CommandException, BankSQLException, BankLoginException {
		assertArgs(args, "string,string");
		String username = args.get(0).getStringArg();
		String password = args.get(1).getStringArg();
		this.state.login(username,password);
		this.userio.sendSuccess("Logged in as: "+username);
	}
	
	private void register(List<Argument> args) throws CommandException, BankStateException, BankSQLException, BankRegisterException{
		assertArgs(args, "string,string");
		String username = args.get(0).getStringArg();
		String password = args.get(1).getStringArg();
		this.state.register(username,Password.hash(password));
		this.userio.sendSuccess("Created new user: "+username);
	}
	
	private void registerSuperuser(List<Argument> args) throws CommandException, BankStateException, BankSQLException, BankRegisterException{
		assertArgs(args, "string,string");
		String username = args.get(0).getStringArg();
		String password = args.get(1).getStringArg();
		this.state.registerSuperuser(username,Password.hash(password));
		this.userio.sendSuccess("Created new superuser: "+username);
	}
	
}
