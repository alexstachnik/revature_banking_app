package com.revature.project0.IO;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Command {

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((args == null) ? 0 : args.hashCode());
		result = prime * result + ((commandType == null) ? 0 : commandType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Command other = (Command) obj;
		if (args == null) {
			if (other.args != null)
				return false;
		} else if (!args.equals(other.args))
			return false;
		if (commandType != other.commandType)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Command [args=" + args + ", commandType=" + commandType + "]";
	}

	public enum CommandType {
		NULL_COMMAND,
		INVALID_COMMAND,
		COMMAND_CLOSE,
		LOGIN_COMMAND,
		LOGOUT_COMMAND,
		REGISTER_COMMAND,
		REGISTER_SUPERUSER_COMMAND,
		VIEW_USERS_COMMAND,
		UPDATE_USER_COMMAND,
		DELETE_USER_COMMAND,
		BALANCE_COMMAND,
		CREATE_ACCOUNT_COMMAND,
		DELETE_ACCOUNT_COMMAND,
		DEPOSIT_COMMAND,
		WITHDRAWL_COMMAND,
		VIEW_ACCOUNTS_COMMAND
		};
		
	private List<Argument> args;
	
	private static final Pattern allWhiteSpace = Pattern.compile("\s*");
	private static final Pattern normalCmd =
			Pattern.compile("\s*([a-zA-Z_]*)(\s+.*)?");

	private CommandType commandType;
	
	private Command(CommandType commandType, List<Argument> args) {
		this.commandType = commandType;
		this.args = args;
  	}
	
	public CommandType getCommandType() {
		return this.commandType;
	}
	
	public List<Argument> getArgs() {
		return args;
	}
	
	public static Command ParseCommand(String line) {
		Matcher wsMatcher = allWhiteSpace.matcher(line);
		if (wsMatcher.hitEnd()) {
			return new Command(CommandType.NULL_COMMAND,null);
		}
		
		Matcher cmdMatcher = normalCmd.matcher(line);
		if (!cmdMatcher.matches()) {
			return new Command(CommandType.INVALID_COMMAND,null);
		}
		
		String args = "";
		if (cmdMatcher.group(2) != null) {
			args=cmdMatcher.group(2);
		}
		
		String[] argarray = args.strip().split(" ");
		List<Argument> list = new ArrayList<Argument>();
		for (int i=0;i<argarray.length;++i) {
			
			try {
				int j=Integer.parseInt(argarray[i]);
				list.add(new IntArgument(j));
			} catch (NumberFormatException e) {
				try {
					double d = Double.parseDouble(argarray[i]);
					list.add(new DoubleArgument(d));
				} catch (NumberFormatException ee) {
					list.add(new StringArgument(argarray[i]));
				}
			}
		}
		
		String command = cmdMatcher.group(1).toLowerCase();
		if (command.equals("login")) {
			return new Command(CommandType.LOGIN_COMMAND,list);
		} else if (command.equals("logout")){
			return new Command(CommandType.LOGOUT_COMMAND,list);
		} else if (command.equals("exit") || command.equals("quit")) {
			return new Command(CommandType.COMMAND_CLOSE,list);
		} else if (command.equals("register")) {
			return new Command(CommandType.REGISTER_COMMAND,list);
		} else if (command.equals("register_superuser")) {
			return new Command(CommandType.REGISTER_SUPERUSER_COMMAND,list);
		} else if (command.equals("view_users")) {
			return new Command(CommandType.VIEW_USERS_COMMAND, list);
		} else if (command.equals("update_user")) {
			return new Command(CommandType.UPDATE_USER_COMMAND,list);
		} else if (command.equals("delete_user")) {
			return new Command(CommandType.DELETE_USER_COMMAND,list);
		} else if (command.equals("balance")) {
			return new Command(CommandType.BALANCE_COMMAND,list);
		} else if (command.equals("create_account")) {
			return new Command(CommandType.CREATE_ACCOUNT_COMMAND,list);
		} else if (command.equals("delete_account")) {
			return new Command(CommandType.DELETE_ACCOUNT_COMMAND,list);
		} else if (command.equals("deposit")) {
			return new Command(CommandType.DEPOSIT_COMMAND,list);
		} else if (command.equals("withdraw")) {
			return new Command(CommandType.WITHDRAWL_COMMAND,list);
		} else if (command.equals("view_accounts")) {
			return new Command(CommandType.VIEW_ACCOUNTS_COMMAND,list);
		}

		return new Command(CommandType.INVALID_COMMAND,null);
	}
	
}
