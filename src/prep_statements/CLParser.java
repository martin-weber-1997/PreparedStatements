package prep_statements;

import java.io.Console;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * parses command line options using Apache Commons CLI 1.31
 * 
 * @author Daniel May
 * @version 20160223.1
 *
 */
public class CLParser {

	private CommandLine cl;
	private Options opt;
	private HelpFormatter formatter;
	private String password;

	/**
	 * constructor
	 */
	public CLParser(String[] args) {
		setUpOptions();
		formatter = new HelpFormatter();
		parseOptions(args);
	}

	/**
	 * setting up the command line options
	 */
	private void setUpOptions() {
		opt = new Options();
		opt.addOption(Option.builder("d").argName("database-name").desc("database name to connect to").hasArg()
				.longOpt("database").numberOfArgs(1).required().build());
		opt.addOption(Option.builder("H").argName("hostname").desc("database server host").hasArg().longOpt("host")
				.numberOfArgs(1).required().build());
		opt.addOption(Option.builder("p").argName("port").desc("database server port").hasArg().longOpt("port")
				.numberOfArgs(1).build());
		opt.addOption(Option.builder("u").argName("username").desc("database user name").hasArg().longOpt("user")
				.numberOfArgs(1).required().build());
		OptionGroup pwGroup = new OptionGroup();
		pwGroup.addOption(Option.builder("w").argName("password").desc("pass password as command line argument")
				.hasArg().longOpt("password").numberOfArgs(1).build());
		pwGroup.addOption(Option.builder("W").desc("force password prompt").longOpt("password-prompt").build());
		pwGroup.setRequired(true);
		opt.addOptionGroup(pwGroup);
	}

	/**
	 * Parse command line arguments. If a ParseException occurs, the error
	 * message and the usage will be printed. The application will be terminated
	 * in that case.
	 * 
	 * @param args
	 *            command line anrguments
	 */
	private void parseOptions(String[] args) {
		DefaultParser parser = new DefaultParser();
		try {
			cl = parser.parse(opt, args);
		} catch (ParseException exp) {
			System.err.println("Parsing failed.  Reason: " + exp.getMessage());
			printUsage();
			System.exit(0);
		}
		interrogate();
	}

	/**
	 * Simple wrapper method in order to print the help message including the
	 * usage.
	 */
	private void printUsage() {
		formatter.printHelp("preps", opt, true);
	}

	/**
	 * Detects if a password prompt is forced and executes the prompt.
	 */
	private void interrogate() {
		if (cl.hasOption('W')) {
			Console console = System.console();
			/*
			 * stackoverflow helped me
			 * http://stackoverflow.com/questions/8138411/masking-password-input
			 * -from-the-console-java
			 */
			if (console == null) {
				System.out.println("Couldn't get console instance!");
				System.exit(0);
			}
			/*
			 * %n is a line separator; %s is for unicode characters
			 */
			password = new String(console.readPassword("Please enter your password: "));
		}
	}

	/**
	 * Gets the database specified by the command line.
	 * 
	 * @return the specified database
	 */
	public String getDatabase() {
		return cl.getOptionValue('d');
	}

	/**
	 * Gets the hostname specified by the command line.
	 * 
	 * @return the specified hostname
	 */
	public String getHost() {
		return cl.getOptionValue('H');
	}

	/**
	 * Gets the username specified by the command line.
	 * 
	 * @return the specified username
	 */
	public String getUser() {
		return cl.getOptionValue('u');
	}

	/**
	 * Detects if the password was specified as argument or entered via password
	 * prompt and returns it.
	 * 
	 * @return the specified password
	 */
	public String getPassword() {
		if (cl.hasOption('w'))
			return cl.getOptionValue('w');
		else
			return password;
	}

	/**
	 * Gets the port if specified by the command line, otherwise the standard
	 * port for PSQL 5432 will be used. If the specified port number is not an
	 * integer the usage will be printed and the application will be terminated.
	 * 
	 * @return the specified port
	 */
	public int getPort() {
		try {
			return Integer.parseInt(cl.getOptionValue('p', "5432"));
		} catch (NumberFormatException nfe) {
			printUsage();
			System.exit(0);
		}
		return 0;
	}

	/**
	 * Sets the password field to null.
	 */
	public void clearPassword() {
		password = null;
	}
}